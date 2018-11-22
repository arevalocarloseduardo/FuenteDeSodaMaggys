package com.yadaapps.caear.pedidosmaggys.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.firebase.database.*
import com.yadaapps.caear.pedidosmaggys.BaseDeDatos
import com.yadaapps.caear.pedidosmaggys.Fragments.AdaptadoresFragments.AdapterFragment
import com.yadaapps.caear.pedidosmaggys.R
import com.yadaapps.caear.pedidosmaggys.Upload
import kotlinx.android.synthetic.main.fragment_pedidos.view.*
import kotlinx.android.synthetic.main.fragment_pedir.view.*

class PedidosFragment : Fragment() {
    lateinit var referenciaConfirmados1 : DatabaseReference
    lateinit var pedidosList1:MutableList<BaseDeDatos>
    lateinit var soloLosPedidos:MutableList<BaseDeDatos>
    lateinit var recyclerPedidos1: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val v=inflater.inflate(R.layout.fragment_pedidos, container, false)
        referenciaConfirmados1 = FirebaseDatabase.getInstance().getReference("Confirmados")
        pedidosList1= mutableListOf()
        soloLosPedidos= mutableListOf()
        recyclerPedidos1=v.recyPedidos
        val nombreDeCliente="Carlos"


        recyclerPedidos1.layoutManager= LinearLayoutManager(activity, LinearLayout.VERTICAL,false)
        val mi2Adapter= AdapterFragment(soloLosPedidos)
        recyclerPedidos1.adapter =mi2Adapter

        referenciaConfirmados1.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){

                    pedidosList1.clear()
                    for (h in p0.children){

                        val hero = h.getValue(BaseDeDatos::class.java)
                        pedidosList1.add(hero!!)
                        for (h in pedidosList1)
                            if (h.cliente==nombreDeCliente){
                                for (h in soloLosPedidos){
                                    val heroId = referenciaConfirmados1.push().key.toString()
                                    val hero = BaseDeDatos(heroId,h.cliente,h.menu,h.llevar,h.cant,h.precio,h.telefono)
                                    recyclerPedidos1.adapter=mi2Adapter
                                }

                            }

                    }
        }


             }})
            return v

}
}