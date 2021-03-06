package com.uist.arfurniture

import android.graphics.Point
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.ar.core.Anchor
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Camera
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Sun
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.categories.*

class MainActivity : AppCompatActivity() {

    private var openedv: Boolean = false


    lateinit var fragment: ArFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutInflater:LayoutInflater = LayoutInflater.from(applicationContext)

        val view: View = layoutInflater.inflate(R.layout.categories,root,false)

        fragment = supportFragmentManager.findFragmentById(R.id.ar_fragment) as ArFragment

        fragment.transformationSystem.apply {  }


        remove_fab.setOnClickListener {
            fragment.transformationSystem.selectedNode?.parent?.let { it1 -> removeAnchorNode(it1) }
        }


        floatingActionButton.setOnClickListener{
            if(openedv==false) {
                root.addView(view, 1)
                floatingActionButton.setImageResource(android.R.drawable.ic_delete)
                openedv = true
            }else{
                root.removeView(view)
                floatingActionButton.setImageResource(android.R.drawable.ic_input_add)
                openedv=false
            }
            recycler_view.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
            recycler_view.adapter = RecyclerAdapter {model_uri: Uri -> addObject(model_uri)}
        }

    }

    private fun removeAnchorNode(nodeRemove: Node) {
        if (nodeRemove is AnchorNode) {
            if (nodeRemove.anchor != null) {
                nodeRemove.anchor!!.detach()
            }
        }

        if (nodeRemove !is Camera && nodeRemove !is Sun) {
            nodeRemove.setParent(null)
        }
    }

    private fun addObject(parse: Uri) {
        val frame = fragment.arSceneView.arFrame
        val point = getScreenCenter()
        if (frame != null) {
            val hits = frame.hitTest(point.x.toFloat(), point.y.toFloat())
            for (hit in hits) {
                val trackable = hit.trackable
                if (trackable is Plane && trackable.isPoseInPolygon(hit.hitPose)) {
                    placeObject(fragment, hit.createAnchor(), parse)
                    break
                }
            }
        }
    }

    private fun placeObject(fragment: ArFragment, createAnchor: Anchor, model: Uri) {
        ModelRenderable.builder()
            .setSource(fragment.context, model)
            .build()
            .thenAccept {
                addNodeToScene(fragment, createAnchor, it)
            }
            .exceptionally {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(it.message)
                    .setTitle("error!")
                val dialog = builder.create()
                dialog.show()
                return@exceptionally null
            }
    }

    private fun addNodeToScene(fragment: ArFragment, createAnchor: Anchor, renderable: ModelRenderable) {
        val anchorNode = AnchorNode(createAnchor)
        val transformableNode = TransformableNode(fragment.transformationSystem)
        transformableNode.scaleController.isEnabled = false
        transformableNode.renderable = renderable
        transformableNode.setParent(anchorNode)
        fragment.arSceneView.scene.addChild(anchorNode)
        transformableNode.select()
    }

    private fun getScreenCenter(): Point {
        val vw = findViewById<View>(android.R.id.content)
        return Point(vw.width / 2, vw.height / 2)
    }
}
