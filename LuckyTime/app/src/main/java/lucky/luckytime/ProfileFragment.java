package lucky.luckytime;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import lucky.luckytime.LoginRegister.LoginActivity;
import lucky.luckytime.Model.Box;
import lucky.luckytime.Model.Promotion;
import lucky.luckytime.Model.User;
import lucky.luckytime.Utils.FirebaseMethods;
import lucky.luckytime.Utils.UniversalImageLoader;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private Context mContext;
    private TextView crip,cemail,cadw,cgb,csb,cbb,tl;
    private ImageView ivgb,ivsb,ivbb;
    private Button btnpro,btnpara,btniban;
    private EditText promotion;
    private ProgressBar mProgressBar;

    private ImageView userpic;
    private TextView username,usermoney,useriban;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods mFirebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userID;
    private ArrayList<Box> boxes;
    private Promotion promo;
    private User mUser;

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_INTENT = 2;
    private UniversalImageLoader universalImageLoader;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.center_profile, container, false);

        mContext = getActivity();
        tl = (TextView) view.findViewById(R.id.tv_tl);
        crip = (TextView) view.findViewById(R.id.tv_rip);
        cemail = (TextView) view.findViewById(R.id.tv_email);
        cadw = (TextView) view.findViewById(R.id.tv_adwatch);
        cgb = (TextView) view.findViewById(R.id.tv_gbcount);
        csb = (TextView) view.findViewById(R.id.tv_sbcount);
        cbb = (TextView) view.findViewById(R.id.tv_bbcount);
        ivgb = (ImageView) view.findViewById(R.id.iv_goldbox);
        ivsb = (ImageView) view.findViewById(R.id.iv_silverbox);
        ivbb = (ImageView) view.findViewById(R.id.iv_bronzbox);
        btnpro = (Button) view.findViewById(R.id.btn_promotion);
        btnpara = (Button) view.findViewById(R.id.btn_paracek);
        btniban = (Button) view.findViewById(R.id.btn_iban);
        promotion = (EditText) view.findViewById(R.id.et_promotion);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        universalImageLoader = new UniversalImageLoader(mContext);
        usermoney = (TextView) view.findViewById(R.id.tv_rip);
        username = (TextView) view.findViewById(R.id.tv_username);
        userpic = (ImageView) view.findViewById(R.id.im_userphoto);
        useriban = (TextView) view.findViewById(R.id.tv_iban);

        mProgressDialog = new ProgressDialog(mContext);
        mFirebaseMethods = new FirebaseMethods(mContext);


        setupFirebaseAuth();

        return view;
    }

    private void setProfilePicture(){
        universalImageLoader.ImageLoadFromUrl(mUser.getPicture(),userpic);

        userpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog customDialog = new Dialog(mContext);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.customdialogpic);
                Button camera = (Button) customDialog.findViewById(R.id.btn_diayes);
                Button galery = (Button) customDialog.findViewById(R.id.btn_diano);

                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (mContext.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                requestPermissions(new String[]{Manifest.permission.CAMERA},
                                        MY_CAMERA_REQUEST_CODE);
                            }else{
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent,GALLERY_INTENT);
                            }
                            customDialog.dismiss();
                        }
                    }
                });
                galery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"Image"),GALLERY_INTENT);
                        customDialog.dismiss();
                    }
                });
                customDialog.show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,GALLERY_INTENT);

            } else {

                Toast.makeText(mContext, "camera permission denied", Toast.LENGTH_LONG).show();

            }

        }
    }
    public String getStars(String source) {
        String bas = source.substring(4,20);
        String newsource = source.replace(bas,"***************");
        return newsource;
    }

    private void setProfilInfo(){

        //setProfilePicture();
        username.setText(mUser.getName());
        usermoney.setText(new DecimalFormat("###,###,###").format(mUser.getRip()));
        if(!mUser.getIban().isEmpty() || !mUser.getIban().equals("")){
            useriban.setVisibility(View.VISIBLE);
            useriban.setText( getStars(mUser.getIban()));
        }else {
            useriban.setVisibility(View.INVISIBLE);
        }
        crip.setText(new DecimalFormat("###,###,###").format(mUser.getRip()));
        tl.setText(new DecimalFormat("###,###.###").format(mUser.getMoney()));
        cadw.setText(new DecimalFormat("###,###,###").format(mUser.getWatch_ad()));
        cgb.setText(String.valueOf(mUser.getAltinbox()));
        csb.setText(String.valueOf(mUser.getGumusbox()));
        cbb.setText(String.valueOf(mUser.getBronzbox()));
        cemail.setText(mUser.getEmail());


        btnpara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUser.getMoney()>20){
                    final Dialog customDialog = new Dialog(mContext);
                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    customDialog.setContentView(R.layout.customdialogparacek);
                    TextView miktar = (TextView) customDialog.findViewById(R.id.input_miktar);
                    TextView question = (TextView) customDialog.findViewById(R.id.input_question);
                    question.setText(mContext.getString(R.string.questionwithdrawel));
                    miktar.setText(String.valueOf(mUser.getMoney()));
                    Button onay = (Button) customDialog.findViewById(R.id.btn_diayes);
                    onay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!mUser.getIban().equals("") || !mUser.getIban().isEmpty()){
                                mFirebaseMethods.savetheRequestWithDrawel(String.valueOf(mUser.getMoney()),mUser.getIban(),mUser.getEmail());
                                Toast.makeText(mContext,getString(R.string.successwithdrawel),Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(mContext,getString(R.string.emptyiban),Toast.LENGTH_LONG).show();
                            }
                            customDialog.dismiss();
                        }
                    });
                    customDialog.show();
                }else{
                    Toast.makeText(mContext,mContext.getString(R.string.paracekwarn),Toast.LENGTH_LONG).show();
                }
            }
        });

        btniban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog customDialog = new Dialog(mContext);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.customdialogiban);
                final EditText iban = (EditText) customDialog.findViewById(R.id.input_iban);
                Button onay = (Button) customDialog.findViewById(R.id.btn_diayes);
                onay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String siban = iban.getText().toString();
                        if(isStringNull(siban))
                            Toast.makeText(mContext,getString(R.string.loginactivity_emptyfield),Toast.LENGTH_LONG).show();
                        else {
                            mFirebaseMethods.saveTheIBANnumber(siban);
                            Toast.makeText(mContext,getString(R.string.savediban),Toast.LENGTH_LONG).show();
                            customDialog.dismiss();
                        }
                    }
                });
                customDialog.show();
            }
        });

        btnpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog customDialog = new Dialog(mContext);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.customdialogpromotion);
                final EditText promotion = (EditText) customDialog.findViewById(R.id.et_promotion);
                Button onay = (Button) customDialog.findViewById(R.id.btn_diayes);
                onay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String spro = promotion.getText().toString();
                        if(isStringNull(spro))
                            Toast.makeText(mContext,getString(R.string.loginactivity_emptyfield),Toast.LENGTH_LONG).show();
                        else {
                            if(promo.isDurum()){
                                if(promo.isUsedcode()){
                                    Toast.makeText(mContext,mContext.getString(R.string.promotionused),Toast.LENGTH_SHORT).show();
                                }else {
                                    int kontrol = 0;
                                    for (int i=0;i<promo.getCodes().size();i++){
                                        if(promo.getCodes().get(i).equals(spro)){
                                            kontrol++;
                                            break;
                                        }
                                    }
                                    if(kontrol==0){
                                        Toast.makeText(mContext,mContext.getString(R.string.promotionwrong),Toast.LENGTH_SHORT).show();
                                    }else {
                                        long newBox;
                                        mFirebaseMethods.promotionUsers(spro);
                                        if(promo.getOdul().equals(mContext.getString(R.string.field_altin))){
                                            newBox = mUser.getAltinbox();
                                            newBox++;
                                            Toast.makeText(mContext,mContext.getString(R.string.gainedgold),Toast.LENGTH_SHORT).show();
                                        }
                                        else if(promo.getOdul().equals(mContext.getString(R.string.field_gumus))){
                                            newBox = mUser.getGumusbox();
                                            Toast.makeText(mContext,mContext.getString(R.string.gainedsilver),Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            newBox = mUser.getBronzbox();
                                            Toast.makeText(mContext,mContext.getString(R.string.gainedbronz),Toast.LENGTH_SHORT).show();
                                        }
                                        newBox++;
                                        mFirebaseMethods.changeBoxCount(promo.getOdul(),newBox);
                                    }
                                }
                            }else {
                                Toast.makeText(mContext,mContext.getString(R.string.promotionwrong),Toast.LENGTH_SHORT).show();
                            }
                            customDialog.dismiss();
                        }
                    }
                });
                customDialog.show();
            }
        });
    }

    private boolean isStringNull(String string){
        if(string.equals("")){
            return true;
        }
        else
            return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_INTENT && resultCode == RESULT_OK){

            mProgressDialog.show();
            Uri uri = data.getData();

            StorageReference filepath = FirebaseStorage.getInstance().getReference().child(mContext.getString(R.string.dbstorage_pp))
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

            if(uri==null){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                bytes.toByteArray();

                filepath.putBytes(bytes.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String photoStringLink = uri.toString();
                                mFirebaseMethods.setProfilePhoto(photoStringLink);
                            }
                        });
                        mProgressDialog.dismiss();
                        Toast.makeText(mContext, mContext.getString(R.string.photouploaded), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mProgressDialog.dismiss();
                        Toast.makeText(mContext, mContext.getString(R.string.photouploadingerror), Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        mProgressDialog.setMessage(mContext.getString(R.string.photouploading) + String.format("%.0f", progress) + "%");
                    }
                });
            }else {

                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String photoStringLink = uri.toString();
                                mFirebaseMethods.setProfilePhoto(photoStringLink);
                            }
                        });
                        mProgressDialog.dismiss();
                        Toast.makeText(mContext, mContext.getString(R.string.photouploaded), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mProgressDialog.dismiss();
                        Toast.makeText(mContext, mContext.getString(R.string.photouploadingerror), Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        mProgressDialog.setMessage(mContext.getString(R.string.photouploading) + String.format("%.0f", progress) + "%");
                    }
                });

            }
        }
    }




    private void getBoxes(){

        for(int i=0;i<boxes.size();i++){
            if(boxes.get(i).getType().equals(mContext.getString(R.string.field_altin))){
                final int finalI = i;
                ivgb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mUser.getAltinbox()>0){
                            askUser(boxes.get(finalI),mContext.getString(R.string.goldinfo),mUser.getAltinbox());
                        }
                        else{
                            Toast.makeText(mContext,mContext.getString(R.string.noboxinfo),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            if(boxes.get(i).getType().equals(mContext.getString(R.string.field_gumus))){
                final int finalI = i;
                ivsb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mUser.getGumusbox()>0){
                            askUser(boxes.get(finalI),mContext.getString(R.string.silverinfo),mUser.getGumusbox());
                        }
                        else{
                            Toast.makeText(mContext,mContext.getString(R.string.noboxinfo),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            if(boxes.get(i).getType().equals(mContext.getString(R.string.field_bronz))){
                final int finalI = i;
                ivbb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mUser.getBronzbox()>0){
                            askUser(boxes.get(finalI),mContext.getString(R.string.bronzinfo),mUser.getBronzbox());
                        }
                        else{
                            Toast.makeText(mContext,mContext.getString(R.string.noboxinfo),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
    private void askUser(final Box box, String type, long boxCount){
        final Dialog customDialog = new Dialog(mContext);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.customdialog);
        TextView question = (TextView) customDialog.findViewById(R.id.tv_dialogque);
        Button yes = (Button) customDialog.findViewById(R.id.btn_diayes);

        question.setText(type +" "+mContext.getString(R.string.wantopen));
        yes.setText(mContext.getString(R.string.yes));
        if(boxCount>0){
            final long finalbox = boxCount;
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long newBox = finalbox;
                    long openbox = box.openBox();
                    newBox--;
                    Toast.makeText(mContext,openbox + " " + mContext.getString(R.string.gainedrip),Toast.LENGTH_SHORT).show();
                    mFirebaseMethods.changeBoxCount(box.getType(), newBox);
                    mFirebaseMethods.changeRipCount(mUser.getRip()+openbox);
                    customDialog.cancel();
                }
            });
        }

        customDialog.show();
    }

    private void readData(final FirebaseCallback firebaseCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User uu = mFirebaseMethods.getUsersInfo(dataSnapshot);
                ArrayList<Box> bb = mFirebaseMethods.getBoxesInfo(dataSnapshot);
                Promotion pp = mFirebaseMethods.getPromotionInfo(dataSnapshot);
                try {
                    firebaseCallback.userInfo(uu,bb,pp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mFirebaseDatabase.getReference().addValueEventListener(valueEventListener);
    }


    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        if(mAuth.getCurrentUser() != null)
            userID = mAuth.getCurrentUser().getUid();
        else{
            Intent intent = new Intent(mContext,LoginActivity.class);
            startActivity(intent);
        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user!= null)
                {

                }else{
                    Intent intent = new Intent(mContext,LoginActivity.class);
                    startActivity(intent);
                }

            }
        };

        readData(new FirebaseCallback() {
            @Override
            public void userInfo(User user,ArrayList<Box> box,Promotion prom) {
                mUser = user;
                boxes = box;
                promo = prom;
                setProfilePicture();
                setProfilInfo();
                mProgressBar.setVisibility(View.INVISIBLE);
                getBoxes();
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener!= null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private interface FirebaseCallback {
        void userInfo(User user, ArrayList<Box> boxes, Promotion promo) throws Exception;
    }
}
