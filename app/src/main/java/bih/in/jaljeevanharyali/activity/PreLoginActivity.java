package bih.in.jaljeevanharyali.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.grievance.GrievanceLoginActivity;
import bih.in.jaljeevanharyali.utility.Utiilties;

public class PreLoginActivity extends Activity {

    TextView tv_appver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);

        tv_appver=findViewById(R.id.tv_appver);

        String version = Utiilties.getAppVersion(this);
        if(version != null){
            tv_appver.setText("ऐप वर्ज़न "+version);
        }else{
            tv_appver.setText("");
        }
    }

    public void onDeptartmentLoginClick(View v)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        //finish();
    }

    public void onComplainSystemClick(View v)
    {
        Intent intent = new Intent(this, GrievanceLoginActivity.class);
        startActivity(intent);
        //finish();
    }
}
