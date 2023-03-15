package fr.epsi.projet_mobile_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class StudentsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students)
        setHeaderTitle(getString(R.string.title_activity_infos))
        showBack()

        val students = arrayListOf<Student>()
        val studentAlexandre = Student("Redon","Alexandre","alexandre.redon1@epsi.fr","Groupe DEV2")
        val studentAnthony = Student("Duret","Anthony","anthony.duret@epsi.fr","Groupe DEV2")
        students.add(studentAlexandre)
        students.add(studentAnthony)


        val buttonInfo=findViewById<Button>(R.id.buttonStudent)
        buttonInfo.setOnClickListener(View.OnClickListener {
            val intent = Intent(application, StudentsDetails::class.java)
            intent.putExtra("nom",students[0].nom)
            intent.putExtra("prenom",students[0].prenom)
            intent.putExtra("email",students[0].email)
            intent.putExtra("groupe",students[0].groupe)
            startActivity(intent)
        })

        val buttonProducts=findViewById<Button>(R.id.buttonStudent2)
        buttonProducts.setOnClickListener(View.OnClickListener {
            val intent = Intent(application, StudentsDetails::class.java)
            intent.putExtra("nom",students[1].nom)
            intent.putExtra("prenom",students[1].prenom)
            intent.putExtra("email",students[1].email)
            intent.putExtra("groupe",students[1].groupe)
            startActivity(intent)
        })

    }
}