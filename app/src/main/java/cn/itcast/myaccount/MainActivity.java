package cn.itcast.myaccount;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.List;

import cn.itcast.myaccount.MyData.AccountItem;
import cn.itcast.myaccount.MyData.DataAccount;

public class MainActivity extends AppCompatActivity {
    private List<AccountItem> accountItems;

    private MyRecyclerViewAdapter recyclerViewAdapter;
    public static final int RESULT_CODE_CHANGE_DATA = 1;

    private DataAccount dataAccount;

    public TextView tv_all_money, tv_now_income, tv_now_pay;

//    public static final int RESULT_CODE_ADD_DATA = RESULT_CODE_MOD_DATA + 1;

//    int position;
    Intent intent;

    ActivityResultLauncher<Intent> launcherAdd = registerForActivityResult( new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult( ActivityResult result ) {
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if ( resultCode == RESULT_CODE_CHANGE_DATA ) {
                if( null == data )
                    return;
                String money = data.getStringExtra("money" );
                int position = data.getIntExtra("position", 0); //accountItems.size() );
                accountItems.add( position, new AccountItem( Integer.parseInt(money), R.drawable.bill ));// 此处考虑选择三个图片中的某个
                dataAccount.saveData();
                updateShowData();
                recyclerViewAdapter.notifyItemInserted(position);

            }
        }
    });
    ActivityResultLauncher<Intent> launcherModify = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult( ActivityResult result ) {
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if( resultCode == RESULT_CODE_CHANGE_DATA ){
                if( null == data )
                    return;
                int money = Integer.parseInt( data.getStringExtra( "money" ) );
                int position = data.getIntExtra( "position", accountItems.size() );
                accountItems.get(position).setMoney( money );

                dataAccount.saveData();
                updateShowData();

                recyclerViewAdapter.notifyItemChanged(position);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        tv_all_money  = findViewById(R.id.all_money);
        tv_now_income = findViewById(R.id.now_month_income);
        tv_now_pay    = findViewById(R.id.now_month_pay);
        updateShowData();

//        tv_all_money.setText("all money");
//        tv_now_income.setText("income");
//        tv_now_pay.setText("pay");

        FloatingActionButton fabAdd = findViewById( R.id.floating_action_button_add );
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                View dialogueView = LayoutInflater.from( MainActivity.this ).inflate( R.layout.dialogue_input_item,null );
//                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( MainActivity.this );
//                        alertDialogBuilder.setView( dialogueView );
//
//                        alertDialogBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                EditText editMoney = dialogueView.findViewById( R.id.edit_text_money );
//                                accountItems.add( 0, new AccountItem( Integer.parseInt( editMoney.getText().toString() )
//                                        , R.drawable.eat ) );
////                                MyRecyclerViewAdapter.this.notifyItemInserted( 0 );// there is a problem
//                            }
//                        });
//                        alertDialogBuilder.setCancelable( false ).setNegativeButton ("cancel",new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        });
//                        alertDialogBuilder.create().show();;

//                        int position = getAdapterPosition();
                        intent = new Intent(MainActivity.this,EditAccountActivity.class );

                        intent.putExtra("money", 0 );
                intent.putExtra("position", 0 );

                launcherAdd.launch( intent );
            }
        });



        RecyclerView mainRecycleView = findViewById( R.id.recycle_view_accounts );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        mainRecycleView.setLayoutManager( layoutManager );

        recyclerViewAdapter = new MyRecyclerViewAdapter( accountItems );
        mainRecycleView.setAdapter( recyclerViewAdapter );// bug
    }

    public void initData(){
        dataAccount = new DataAccount( MainActivity.this );
        accountItems = dataAccount.loadData();

//        accountItems = new ArrayList<AccountItem>();
//        accountItems.add( new AccountItem( 50,R.drawable.day_use ) );
//        accountItems.add( new AccountItem(30,R.drawable.eat ) );
//        accountItems.add( new AccountItem(20,R.drawable.income ) );
    }

    public void updateShowData() {
        int sum, income, pay;

        sum = 0; income = 0; pay = 0;
        for (int i = 0; i < accountItems.size(); ++i) {
            int tmp = accountItems.get( i ).getMoney();
            sum += tmp;
            if (tmp < 0) {
                pay += tmp;
            } else {
                income += tmp;
            }
        }
        tv_all_money.setText(String.valueOf(sum));
        tv_now_income.setText(String.valueOf(income));
        tv_now_pay.setText(String.valueOf(pay));
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter {
        private List<AccountItem> accountItems;

        public MyRecyclerViewAdapter( List<AccountItem> accountItems ) {
            this.accountItems = accountItems;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
            //bug
            View view = LayoutInflater.from( parent.getContext() )
                    .inflate( R.layout.account_item_holder, parent, false );

            return new MyViewHolder( view );
        }

        @Override
        public void onBindViewHolder( @NonNull RecyclerView.ViewHolder Holder, int position ) {
            MyViewHolder holder = (MyViewHolder) Holder;

            holder.getImageView().setImageResource( accountItems.get( position ).getResourceId() );
            holder.getTextViewName().setText( String.valueOf( accountItems.get( position ).getMoney() ) );
        }

        @Override
        public int getItemCount() {
            return accountItems.size();
        }

        private class MyViewHolder extends RecyclerView.ViewHolder
                implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
            private final ImageView imageView;
            private final TextView textViewMoney;
            private final int SWITCH_MODIFY = 1;
            private final int SWITCH_DEL    = SWITCH_MODIFY + 1;

            public MyViewHolder( View view ) {
                super(view);

                this.imageView = view.findViewById( R.id.image_view_account );
                this.textViewMoney = view.findViewById( R.id.text_view_money );

                itemView.setOnCreateContextMenuListener( this );
            }

            public ImageView getImageView() {
                return imageView;
            }

            public TextView getTextViewName() {
                return textViewMoney;
            }

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                int position=getAdapterPosition();
                MenuItem menuItemModify    = contextMenu.add( Menu.NONE,SWITCH_MODIFY,SWITCH_MODIFY,"Modify"+position );
                MenuItem menuItemDelete = contextMenu.add( Menu.NONE,SWITCH_DEL,SWITCH_DEL,"Delete"+position );

                menuItemModify.setOnMenuItemClickListener( this );
                menuItemDelete.setOnMenuItemClickListener( this );
            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int position = getAdapterPosition();
                switch( menuItem.getItemId() )
                {
                    case SWITCH_MODIFY:
                        intent = new Intent( MainActivity.this,EditAccountActivity.class );
                        intent.putExtra( "position", position );
                        intent.putExtra( "money", String.valueOf( accountItems.get(position).getMoney() ) );
                        launcherModify.launch( intent );
                        break;
                    case SWITCH_DEL:
                        accountItems.remove( position );
                        dataAccount.saveData();
                        updateShowData();
                        MyRecyclerViewAdapter.this.notifyItemRemoved( position );
                        break;
                }

                return false;
            }
        }
    }
}