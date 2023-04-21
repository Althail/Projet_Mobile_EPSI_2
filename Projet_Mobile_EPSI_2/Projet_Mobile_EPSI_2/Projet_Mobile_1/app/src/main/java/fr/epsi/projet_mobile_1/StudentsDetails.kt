package fr.epsi.projet_mobile_1

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso

class StudentsDetails : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students_details)

        showBack()

        val bundle : Bundle?= intent.extras
        val nom = bundle!!.getString("nom")
        val prenom = bundle.getString("prenom")
        val email = bundle.getString("email")
        val groupe = bundle.getString("groupe")
        val prenomEntier = "$prenom $nom"
        setHeaderTitle(nom)


        val textViewNomPrenom = findViewById<TextView>(R.id.nameView)
        val textViewEmail = findViewById<TextView>(R.id.emailView)
        val textViewGroupe = findViewById<TextView>(R.id.groupeView)
        val buttonEpsi = findViewById<Button>(R.id.buttonEpsi)
        val imageViewStudent = findViewById<ImageView>(R.id.imageViewStudent)


        textViewNomPrenom.text = prenomEntier
        textViewGroupe.text = groupe
        textViewEmail.text = email
        if(prenom == "Alexandre") {
            Picasso.get().load(R.drawable.alexandre_user).into(imageViewStudent)
        } else {
            Picasso.get().load(R.drawable.icon_user).into(imageViewStudent)
        }


        buttonEpsi.setOnClickListener {
            var lienVersEpsi = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.epsi.fr/"))
            startActivity(lienVersEpsi)
        }


    }
}