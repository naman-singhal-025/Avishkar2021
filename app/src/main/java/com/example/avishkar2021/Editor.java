package com.example.avishkar2021;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Editor extends AppCompatActivity {

    private EditText edittext;

    private ImageButton boldB,italicB,underLineB,leftAlignB,centerAlignB,rightAlignB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        edittext=findViewById(R.id.editTextTextMultiLine);
        boldB=findViewById(R.id.imageButton);
        italicB=findViewById(R.id.imageButton2);
        underLineB=findViewById(R.id.imageButton3);
        leftAlignB=findViewById(R.id.imageButton4);
        rightAlignB=findViewById(R.id.imageButton6);
        centerAlignB=findViewById(R.id.imageButton5);
    }


    public void saveButton(View view) {

        Toast.makeText(this, "save button", Toast.LENGTH_SHORT).show();
    }

    public void boldButton(View view) {
        int start= edittext.getSelectionStart();
        int end=edittext.getSelectionEnd();
        Spannable str=edittext.getText();

        str.setSpan(new StyleSpan(Typeface.BOLD),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        edittext.setText(str);
    }

    public void italicButton(View view) {

        int start= edittext.getSelectionStart();
        int end=edittext.getSelectionEnd();
        Spannable str=edittext.getText();

        str.setSpan(new StyleSpan(Typeface.ITALIC),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        edittext.setText(str);
    }

    public void underlineButton(View view) {
        int start= edittext.getSelectionStart();
        int end=edittext.getSelectionEnd();
        Spannable str=edittext.getText();

        str.setSpan(new UnderlineSpan(),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        edittext.setText(str);
    }

    public void leftAlignButton(View view) {
        centerAlignB.setBackgroundColor(getResources().getColor(R.color.white));
        rightAlignB.setBackgroundColor(getResources().getColor(R.color.white));
        leftAlignB.setBackgroundColor(getResources().getColor(R.color.ocen_blue));

        edittext.setGravity(Gravity.LEFT);
    }

    public void centerAlignButton(View view) {
        rightAlignB.setBackgroundColor(getResources().getColor(R.color.white));
        leftAlignB.setBackgroundColor(getResources().getColor(R.color.white));
        centerAlignB.setBackgroundColor(getResources().getColor(R.color.ocen_blue));
        edittext.setGravity(Gravity.CENTER);
    }

    public void rightAlignButton(View view) {
        leftAlignB.setBackgroundColor(getResources().getColor(R.color.white));
        centerAlignB.setBackgroundColor(getResources().getColor(R.color.white));
        rightAlignB.setBackgroundColor(getResources().getColor(R.color.ocen_blue));
        edittext.setGravity(Gravity.RIGHT);
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void keyBoardButton(View view) {
        hideKeyboard(this);
    }
}