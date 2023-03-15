package fr.epsi.projet_mobile_1


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView


class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setHeaderTitle(getString(R.string.main_title))

        val buttonInfo=findViewById<Button>(R.id.buttonInfo)
        buttonInfo.setOnClickListener(View.OnClickListener {
            val newIntent = Intent(application, StudentsActivity::class.java)
            startActivity(newIntent)
        })

        val buttonProducts=findViewById<Button>(R.id.buttonProducts)
        buttonProducts.setOnClickListener(View.OnClickListener {
            val newIntent = Intent(application, RayonActivity::class.java)
            startActivity(newIntent)
        })
    }



}