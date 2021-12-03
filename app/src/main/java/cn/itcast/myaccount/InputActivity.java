package cn.itcast.myaccount;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

//public class InputActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //setContentView( R.layout.activity_input );
//
////        Intent intent = getIntent();
////        int position = intent.getIntExtra("position", 0 );
////
////        EditText editTextMoney = findViewById( R.id.edit_text_money );
////        String money = intent.getStringExtra( "money" );
////        if( null != money ) {
////            editTextMoney.setText( money );
////        }
////
////        Button buttonOk = this.findViewById(R.id.button_ok);
////        buttonOk.setOnClickListener(view -> {
////            Intent intent1 = new Intent();
////            intent1.putExtra( "position", position );
////            intent1.putExtra( "money", editTextMoney.getText().toString() );
////            setResult( MainActivity.RESULT_CODE_ADD_DATA, intent1 );
////            InputActivity.this.finish();
////        });
//    }
//}
