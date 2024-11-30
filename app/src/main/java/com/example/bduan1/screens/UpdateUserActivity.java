package com.example.bduan1.screens;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bduan1.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class UpdateUserActivity extends AppCompatActivity {

    private Button btnUpdate;
    private TextInputLayout textInputName, textInputPhoneNumber, textInputBirth;
    private TextInputEditText edtName, edtPhoneNumber, edtBirthDate;
    private ImageView imgBackUpdateUser;
    private RadioButton rdMale,rdFemale;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initUi();
        rdMale.setChecked(true);
        btnUpdate.setOnClickListener(v -> {
            if (validateInputs()) {
                // Nếu dữ liệu hợp lệ, tiến hành cập nhật
                Toast.makeText(this, "Dữ liệu hợp lệ. Đang cập nhật...", Toast.LENGTH_SHORT).show();
                // TODO: Thực hiện hành động cập nhật ở đây
            }
        });
        imgBackUpdateUser.setOnClickListener(v -> {finish();});
    }

    private void initUi() {
        btnUpdate = findViewById(R.id.btnUpdate_user);
        textInputName = findViewById(R.id.textInputName);
        textInputPhoneNumber = findViewById(R.id.textInputPhoneNumber);
        textInputBirth = findViewById(R.id.textInputBirth);
        edtName = findViewById(R.id.edtName_update);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber_update);
        edtBirthDate = findViewById(R.id.edtBirth_update);
        imgBackUpdateUser = findViewById(R.id.imgBackUpdateUser);
        rdMale = findViewById(R.id.radioMale);
        rdFemale = findViewById(R.id.radioFemale);
    }

    private boolean validateInputs() {
        boolean isValid = true;
        // Kiểm tra trường tên
        if (TextUtils.isEmpty(edtName.getText())) {
            textInputName.setError("Vui lòng nhập tên");
            isValid = false;
        } else {
            textInputName.setError(null); // Xóa lỗi nếu có
        }

        // Kiểm tra trường số điện thoại
        if (TextUtils.isEmpty(edtPhoneNumber.getText())) {
            textInputPhoneNumber.setError("Vui lòng nhập số điện thoại");
            isValid = false;
        } else {
            textInputPhoneNumber.setError(null);
        }

        // Kiểm tra trường ngày sinh
        if (TextUtils.isEmpty(edtBirthDate.getText())) {
            textInputBirth.setError("Vui lòng nhập ngày sinh");
            isValid = false;
        } else {
            textInputBirth.setError(null);
        }

        return isValid;
    }
}
