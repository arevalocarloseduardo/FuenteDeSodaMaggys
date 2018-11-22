package com.yadaapps.caear.pedidosmaggys.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.firebase.database.*
import com.yadaapps.caear.pedidosmaggys.*
import com.yadaapps.caear.pedidosmaggys.Fragments.AdaptadoresFragments.AdapterFragment
import com.yadaapps.caear.pedidosmaggys.R
import kotlinx.android.synthetic.main.fragment_pedir.view.*

class PedirFragment : Fragment() {

    lateinit var referenciaImagenes : DatabaseReference
    lateinit var referenciaPedidos : DatabaseReference

    lateinit var pedidosList:MutableList<BaseDeDatos>
    lateinit var imagenList:MutableList<Upload>

    lateinit var recyclerImagenes: RecyclerView
    lateinit var recyclerPedidos: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val v= inflater.inflate(R.layout.fragment_pedir, container, false)

        referenciaImagenes = FirebaseDatabase.getInstance().getReference("usuarios")
        referenciaPedidos = FirebaseDatabase.getInstance().getReference("Pedidos")
        imagenList= mutableListOf()
        pedidosList= mutableListOf()
        recyclerPedidos=v.listaView
        recyclerImagenes=v.rv_menus


        recyclerImagenes.layoutManager=LinearLayoutManager(activity,LinearLayout.HORIZONTAL,false)
        val miAdapter= RecyclerAdapter(imagenList)
        recyclerImagenes.adapter =miAdapter

        recyclerPedidos.layoutManager=LinearLayoutManager(activity,LinearLayout.VERTICAL,false)
        val mi2Adapter= AdapterFragment(pedidosList)
        recyclerPedidos.adapter =mi2Adapter
//******************************Pasamos todos los atributos de la base de datos a la lista de imagenes******************

        referenciaImagenes.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                imagenList.clear()
                if(p0.exists()){
                    for (h in p0.children){
                        val uplo = h.getValue(Upload::class.java)
                        imagenList.add(uplo!!)
                        recyclerImagenes.adapter=miAdapter
                    }
                    for (h in imagenList){
                    }
                }else{
                    imagenList.clear()
                }
            }
        })

//*******************************Pasamos todos los atributos de la base de datos a la lista de pedidos***********************************************


        referenciaPedidos.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {

                if(p0.exists()){
                    pedidosList.clear()
                    for (h in p0.children){
                        val hero = h.getValue(BaseDeDatos::class.java)
                        pedidosList.add(hero!!)
                    }
                    recyclerPedidos.adapter=mi2Adapter
                    //for (h in heroList){
                    //   precioTotal =precioTotal+ (h.cant.toInt() * h.precio.toInt())
                    //}
                    //   tvPrecioTotal.text=precioTotal.toString()
                }
                else{
                    pedidosList.clear()
                    recyclerPedidos.adapter=mi2Adapter
                    // precioTotal = 0
                    //for (h in heroList){

                    //     precioTotal =precioTotal + (h.cant.toInt() * h.precio.toInt())
                    //  }
                    //   tvPrecioTotal.text=precioTotal.toString()

                }
            }
        }
        )


        //val btn =v.btnFragment
       /* btn.setOnClickListener {
            val frag2 = GaleryFragment()

            fragmentManager
                ?.beginTransaction()
                ?.add(R.id.frameConta,frag2)
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ?.commit()
            // val manager=fragmentManager
            // val frag_transition = manager?.beginTransaction()
            // frag_transition?.add(R.id.frameConta,frag2)
            // frag_transition?.commit()
        }*/
        return v
    }
    companion object {
        fun newInstance(): PedirFragment{
            val fragment=PedirFragment()
            return fragment
        }
    }
}

