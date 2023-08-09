package com.example.tugasakhir;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tugasakhir.databinding.ActivityRegisterBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Register extends AppCompatActivity {

    ActivityRegisterBinding binding;
    ArrayList<Customer> datacust = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try
        {
            this.getSupportActionBar().hide();
//            this.getSupportActionBar().setTitle("Register");
        }
        catch (NullPointerException e){}

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.txtPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    binding.txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.imageView4.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                }
                else{
                    binding.txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.imageView4.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                }
            }
        });

        binding.imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.txtConfPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    binding.txtConfPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.imageView5.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                }
                else{
                    binding.txtConfPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.imageView5.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                }
            }
        });

        binding.buttonRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.txtNama.getText().toString();
                String email = binding.txtEmail.getText().toString();
                String username = binding.txtUsernameRegis.getText().toString();
                String password = binding.txtPassword.getText().toString();
                String confirmpass = binding.txtConfPassword.getText().toString();
                String role = binding.spinnerRegisRole.getSelectedItem().toString();

                if(name.equalsIgnoreCase("") || email.equalsIgnoreCase("") || username.equalsIgnoreCase("")
                || password.equalsIgnoreCase("") || confirmpass.equalsIgnoreCase("")){
                    Toast.makeText(Register.this, "Ada field kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(password.equals(confirmpass)){
                        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            if (role.equalsIgnoreCase("Customer")) {
                                StringRequest stringRequest = new StringRequest(
                                        Request.Method.POST,
                                        getResources().getString(R.string.url),
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                System.out.println(response);

                                                try {
                                                    JSONObject jsonobject = new JSONObject(response);
                                                    JSONArray arrtemp = jsonobject.getJSONArray("datacust");
                                                    for (int j = 0; j <= arrtemp.length() - 1; j++) {
                                                        JSONObject tempMenu = arrtemp.getJSONObject(j);
                                                        Customer cust = new Customer(
                                                                tempMenu.getString("id_customer"),
                                                                tempMenu.getString("username_customer"),
                                                                tempMenu.getString("password_customer"),
                                                                tempMenu.getString("nama_customer"),
                                                                tempMenu.getString("email_customer"),
                                                                tempMenu.getString("status_customer"));
                                                        datacust.add(cust);
                                                    }
                                                    if (datacust.size() <= 0) {
                                                        Customer cust = new Customer(
                                                                username,
                                                                password,
                                                                name,
                                                                email,
                                                                "Aktif"
                                                        );
                                                        AddCustomerProcess(cust);
                                                        Toast.makeText(Register.this, "Berhasil Register", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        boolean ada = false;
                                                        for (int i = 0; i < datacust.size(); i++) {
                                                            if (datacust.get(i).getUsername().equals(username)) {
                                                                ada = true;
                                                                Toast.makeText(Register.this, "Username sudah ada", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                        if (ada == false) {
                                                            Customer cust = new Customer(
                                                                    username,
                                                                    password,
                                                                    name,
                                                                    email,
                                                                    "Aktif"
                                                            );
                                                            AddCustomerProcess(cust);
                                                            Toast.makeText(Register.this, "Berhasil Register", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(Register.this, "Username sudah ada", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }
                                ) {
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("function", "selectAllCust");
                                        return params;
                                    }

                                };
                                RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                                requestQueue.add(stringRequest);

                            } else {
                                Intent i = new Intent(Register.this, RegisterRestoran.class);
                                i.putExtra("NamaResto", name);
                                i.putExtra("UsernameResto", username);
                                i.putExtra("PasswordResto", password);
                                i.putExtra("EmailResto", email);
                                startActivity(i);
                            }

                            try {
                                String senderemail = "florencia.n20@mhs.istts.ac.id";
                                String receiveremail = email;
                                String passwordsenderemail = "ezrkgkdbdzcuitzi";

                                String stringhost = "smtp.gmail.com";
                                Properties properties = System.getProperties();
                                properties.put("mail.smtp.host", stringhost);
                                properties.put("mail.smtp.port", "465");
                                properties.put("mail.smtp.ssl.enable", "true");
                                properties.put("mail.smtp.auth", "true");

                                javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                                    @Override
                                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication(senderemail, passwordsenderemail);
                                    }
                                });

                                MimeMessage mimeMessage = new MimeMessage(session);
                                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiveremail));
                                mimeMessage.setSubject("Register in Restaurant Reservation App");
                                mimeMessage.setText("Verification Registration");

                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Transport.send(mimeMessage);
                                        } catch (MessagingException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                thread.start();

                            } catch (AddressException e) {
                                e.printStackTrace();
                            } catch (MessagingException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            Toast.makeText(Register.this, "Email tidak valid", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(Register.this, "Password dan Confirm Password tidak sama", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void AddCustomerProcess(Customer customer) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                getResources().getString(R.string.url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("function", "RegisterCust");
                params.put("username", customer.getUsername());
                params.put("password", customer.getPassword());
                params.put("nama", customer.getNama());
                params.put("email", customer.getEmail());
                params.put("status", customer.getStatus());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
        requestQueue.add(stringRequest);
    }
}
