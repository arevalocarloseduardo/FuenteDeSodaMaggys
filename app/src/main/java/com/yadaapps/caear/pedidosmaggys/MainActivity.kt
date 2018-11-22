package com.yadaapps.caear.pedidosmaggys

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var referenciaPedidos : DatabaseReference
    lateinit var referenciaConfirmados :DatabaseReference
    lateinit var referenciaImagenes :DatabaseReference

    lateinit var heroList:MutableList<BaseDeDatos>
    lateinit var imagenList:MutableList<Upload>

    lateinit var recyclerImagenes:RecyclerView
    lateinit var listView:ListView

    lateinit var button: Button
    lateinit var editText:EditText
    lateinit var edTelephone:EditText
    lateinit var tvPrecioTotal:TextView
    private lateinit var linearLayoutManager: LinearLayoutManager

    var precioTotal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//***********************************Instanciamos objetos*******************************************************************
        referenciaPedidos = FirebaseDatabase.getInstance().getReference("Pedidos")
        referenciaConfirmados=FirebaseDatabase.getInstance().getReference("Confirmado")
        referenciaImagenes = FirebaseDatabase.getInstance().getReference("usuarios")

        heroList= mutableListOf()
        imagenList= mutableListOf()
        recyclerImagenes=rv_menus
        recyclerImagenes.layoutManager = LinearLayoutManager(this,LinearLayout.HORIZONTAL,false)

        val miAdapter=RecyclerAdapter(imagenList)

        recyclerImagenes.adapter=miAdapter

        editText=etNombre
        edTelephone=etTel
        button=btnEnviar

        tvPrecioTotal = tvPrecio

        listView=listaView


//***********************************Añadimos metodos*******************************************************************

        button.setOnClickListener {
            apretaBtn()
        }
///////////IMAGENES/////////////////////
        var miimagen="https://www.google.com.ar/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png"
        referenciaImagenes.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

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

               // Picasso.get().load(miimagen).into(image1)
            }
        })
        referenciaPedidos.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                if(p0.exists()){
                    heroList.clear()
                    for (h in p0.children){
                        val hero = h.getValue(BaseDeDatos::class.java)
                        heroList.add(hero!!)
                    }

                    val adapter = DatosAdapter(applicationContext, R.layout.datos,heroList)
                    listView.adapter = adapter
                    //for (h in heroList){

                     //   precioTotal =precioTotal+ (h.cant.toInt() * h.precio.toInt())
                    //}
                 //   tvPrecioTotal.text=precioTotal.toString()
                }
                else{
                    heroList.clear()
                    val adapter = DatosAdapter(applicationContext, R.layout.datos,heroList)
                    listView.adapter = adapter
                   // precioTotal = 0
                    //for (h in heroList){

                   //     precioTotal =precioTotal + (h.cant.toInt() * h.precio.toInt())
                  //  }
                 //   tvPrecioTotal.text=precioTotal.toString()

                }
            }
        }
        )


    }


    /* private fun AgregarPedido1() {

         var paraLlevar: String
         if (checkBox1.isChecked){
             paraLlevar="Para LLevar"}
         else {
             paraLlevar = ""
         }
         var cant=et_cant1.text.toString().trim()

         val heroId = referenciaPedidos.push().key.toString()
         //heroList.add(BaseDeDatos("1","","Sopa + Arroz con Pollo",paraLlevar,cant))
         // val adapter = DatosAdapter(applicationContext, R.layout.datos,heroList)
         //listView.adapter = adapter

         val hero = BaseDeDatos(heroId,"","Sopa + Arroz con Pollo",paraLlevar,cant,"150","")

         referenciaPedidos.child(heroId).setValue(hero).addOnCompleteListener(){
             Toast.makeText(applicationContext,"hola",Toast.LENGTH_LONG).show()

             precioTotal = 0
             for (h in heroList){
                 precioTotal =precioTotal+ (h.cant.toInt() * h.precio.toInt())
             }
             tvPrecioTotal.text=precioTotal.toString()
         }
     }*/
    private fun  apretaBtn() {

        var numCliente = editText.text.toString().trim()
        var telefono= edTelephone.text.toString().trim()

        for (h in heroList){
            val heroId = referenciaPedidos.push().key.toString()
            val hero = BaseDeDatos(numCliente,h.cliente,"Sopa + Arroz con Pollo",h.llevar,h.cant,h.precio,telefono)
            referenciaConfirmados.child(heroId).setValue(hero)
            //cambio

        }
        referenciaPedidos.removeValue()
    }
}

