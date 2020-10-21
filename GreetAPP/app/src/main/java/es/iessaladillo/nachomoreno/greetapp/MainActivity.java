package es.iessaladillo.nachomoreno.greetapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;
import es.iessaladillo.nachomoreno.greetapp.databinding.MainActivityBinding;
import utils.SoftInputUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MainActivityBinding binding;
    private int times;
    private String gender = "Mr.";
    private int i = 0;
    private int charsLeft1 = 20;
    private int charsLeft2 = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        binding.txtChars2.setText(charsLeft1 + " chars");
        setContentView(binding.getRoot());
        setupViews();
    }


    private void chars(){

        binding.txtNameIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                charsLeft1 = 20 - binding.txtNameIn.length();
                binding.txtChars.setText(getResources().getQuantityString(R.plurals.main_times, charsLeft1, charsLeft1));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.txtSirnameIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                charsLeft2 = 20 - binding.txtSirnameIn.length();
                binding.txtChars2.setText(getResources().getQuantityString(R.plurals.main_times, charsLeft2, charsLeft2));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    private void swapGender() {

        if (binding.rdbMr.isChecked()) {
            binding.imgShow.setImageResource(R.drawable.ic_mr);
            gender = "Mr.";
        } else if (binding.rdbMrs.isChecked()) {
            binding.imgShow.setImageResource(R.drawable.ic_mrs);
            gender = "Mrs.";
        } else if (binding.rdbMs.isChecked()) {
            binding.imgShow.setImageResource(R.drawable.ic_ms);
            gender = "Ms.";
        }
    }

    private void setupViews() {
        binding.btnGreet.setOnClickListener(this);
        binding.btnGreet.setText("Greet");
        showTimes();


        binding.rdbMr.setOnCheckedChangeListener((x, y) -> swapGender());
        binding.rdbMrs.setOnCheckedChangeListener((x, y) -> swapGender());
        binding.rdbMs.setOnCheckedChangeListener((x, y) -> swapGender());

        binding.btnGreet.setOnClickListener(v -> Greet());

        binding.premium.setOnClickListener(v -> isPremium());

        binding.txtChars.setText(charsLeft1 + " chars");
        chars();


        binding.txtNameIn.setOnFocusChangeListener((v, hasFocus) -> {
            changeTextViewColorOnFocus(hasFocus);
        });

        binding.txtSirnameIn.setOnFocusChangeListener((v, hasFocus) -> {
            changeTextViewColorOnFocus2(hasFocus);
        });

        binding.txtNameIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateUsername();
            }
        });

        binding.txtSirnameIn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                validateSirname();
            }
        });

        binding.txtSirnameIn.setOnEditorActionListener((v, actionId, event) ->
                txtPasswordOnEditorAction(actionId));
    }

    private boolean txtPasswordOnEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            Greet();
            return true;
        }
        return false;
    }


    private void validateSirname() {
        if (binding.txtSirnameIn.getText().toString().isEmpty()){
            binding.txtSirnameIn.setError(getString(R.string.main_required));
        }
    }

    private void validateUsername() {
        if (binding.txtNameIn.getText().toString().isEmpty()){
            binding.txtNameIn.setError(getString(R.string.main_required));
        }
    }


    private void changeTextViewColorOnFocus(boolean hasFocus) {
        if (hasFocus) {
            binding.txtChars.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            binding.txtChars.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void changeTextViewColorOnFocus2(boolean hasFocus) {
        if (hasFocus) {
            binding.txtChars2.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            binding.txtChars2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }


    private void Greet() {
        SoftInputUtils.hideSoftKeyboard(binding.txtSirnameIn);
        if (!binding.txtNameIn.getText().toString().isEmpty() && !binding.txtSirnameIn.getText().toString().isEmpty()) {
            if (binding.poliChck.isChecked()) {
                Toast.makeText(this, "Good afternoon " + gender + " " + binding.txtNameIn.getText().toString() +
                        " " + binding.txtSirnameIn.getText().toString() + ", i hope you're having a wonderful day.", Toast.LENGTH_SHORT).show();
                binding.pbar.setProgress(i++);
                times++;
            } else {
                Toast.makeText(this, "Yo " + gender + binding.txtNameIn.getText().toString()
                        + " " + binding.txtSirnameIn.getText().toString() + ", what's up homie?", Toast.LENGTH_SHORT).show();
                binding.pbar.setProgress(i++);
                times++;
            }

            if (i > 10) {
                binding.txtSaludo1.setText("Buy premium suscription to go on greeting!!");
            } else if (i < 11) {
                binding.lblTimes.setText(i + " of 10");
            }


        }

        else if (binding.txtNameIn.getText().toString().isEmpty() && binding.txtSirnameIn.getText().toString().isEmpty()){
            validateUsername();
            binding.txtNameIn.requestFocus();
        }else if (binding.txtNameIn.getText().toString().isEmpty()) {
            validateUsername();
            binding.txtNameIn.requestFocus();
        } else if(binding.txtSirnameIn.getText().toString().isEmpty()){
            validateSirname();
            binding.txtSirnameIn.requestFocus();
        }
    }


    private void isPremium() {
        if (binding.premium.isChecked()) {
            binding.pbar.setVisibility(View.GONE);
            binding.lblTimes.setVisibility(View.GONE);
            i = 0;
            reset();
        } else {
            binding.pbar.setVisibility(View.VISIBLE);
            binding.lblTimes.setVisibility(View.VISIBLE);
            binding.pbar.setProgress(0);
            binding.lblTimes.setText(i + " of 10");
        }
    }

    public void onClick(View v) {
        times++;
        showTimes();
    }

    private void reset() {
        Greet();
    }

    private void showTimes() {
        if (times < 11) {
            binding.lblTimes.setText(i + " of 10");
        }

        binding.txtSaludo1.setVisibility(View.VISIBLE);
        binding.txtSaludo1.setText(" ");
    }
}