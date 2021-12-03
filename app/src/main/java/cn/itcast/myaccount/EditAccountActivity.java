package cn.itcast.myaccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditAccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_edit_account );

        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0 );

        EditText editTextMoney = findViewById( R.id.edit_text_money );
        String money = intent.getStringExtra( "money" );
        if( null != money ) {
            editTextMoney.setText( money );
        }

        Button buttonOk = this.findViewById( R.id.button_ok );
        Button buttonCancel = this.findViewById( R.id.button_cancel );

        buttonOk.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent();
                intent.putExtra( "position", position );
                intent.putExtra( "money", editTextMoney.getText().toString() );
                setResult( MainActivity.RESULT_CODE_CHANGE_DATA, intent );
                EditAccountActivity.this.finish();
            }
        });

        buttonCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditAccountActivity.this.finish();
            }
        });
    }

}
