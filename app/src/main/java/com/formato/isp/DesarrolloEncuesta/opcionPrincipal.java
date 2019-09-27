package com.formato.isp.DesarrolloEncuesta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.formato.isp.GestionEmpresa.buscar_empresa;
import com.formato.isp.GestionEmpresa.registroEmpresa;
import com.formato.isp.MenuLateral.menuprincipal;
import com.formato.isp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class opcionPrincipal extends AppCompatActivity {

    private static final int MAX_STEP = 4;
    private AppBarConfiguration mAppBarConfiguration;
    private opcionPrincipal actividad;
    private ViewPager viewPager;
    private Button btnNext, btnAnterior;
    private MyViewPagerAdapter myViewPagerAdapter;
    private String about_title_array[] = {
            "Bienvenido al Formulario de diligenciamiento del ISIP.",
            "Registro de unidad productiva",
            "Una vez registrada y guardada podrás dar inicio a la encuesta en sus 4 componentes",
            "Una vez terminado el formulario, te saldrá una pantalla con los resultados de la unidad productiva en los 4 componentes y sus scores"
    };
    private String about_description_array[] = {
            "Es un proceso muy sencillo ya que la aplicación te guiará paso a paso lo que debes hacer.\n¿Empezamos?",
            "Si la unidad productiva aún no ha sido registrada, debes hacerlo presionando el botón que dice 'Registrar unidad productiva'",
            "Debes contar con suficiente tiempo para diligenciar el formulario y con más de una persona de la unidad productiva encuestada. Tener a la mano la mayor cantidad de documentación para realizar verificación visual de lo que aquí se consigna. Podrás navegar de adelante hacia atrás y atrás hacia adelante y guardar los avances.",
            "A su vez podrás acceder a la pantalla de plan de fortalecimiento productivo de la Alianza.",
    };
    private int about_images_array[] = {
            R.drawable.img_wizard_1,
            R.drawable.img_wizard_2,
            R.drawable.img_wizard_3,
            R.drawable.img_wizard_4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcion_principal);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnAnterior = findViewById(R.id.btn_anterior);

        // adding bottom dots
        bottomProgressDots(0);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        viewPager.setClipToPadding(false);
        viewPager.setPadding(0, 0, 0, 0);
        viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin_overlap));
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (viewPager.getCurrentItem() == about_title_array.length - 1) {
                    btnNext.setText("CONSULTAR UNIDAD PRODUCTIVA");
                    btnAnterior.setText("REGISTRAR UNIDAD PRODUCTIVA");

                } else {
                    btnNext.setText("SIGUIENTE");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });



        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnAnterior.getText().equals("REGISTRAR UNIDAD PRODUCTIVA")){
                    Intent abrirRegistro = new Intent(view.getContext(), registroEmpresa.class);
                    startActivity(abrirRegistro);
                }else {
                    int current = viewPager.getCurrentItem() - 1;
                    if (current < MAX_STEP) {
                        // move to next screen
                        viewPager.setCurrentItem(current);
                    }
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnNext.getText().equals("CONSULTAR UNIDAD PRODUCTIVA")){
                    Intent intent = new Intent(v.getContext(), buscar_empresa.class);
                    startActivity(intent);
                }
                else{
                    int current = viewPager.getCurrentItem() + 1;
                    if (current < MAX_STEP) {
                        // move to next screen
                        viewPager.setCurrentItem(current);
                    }
                }
            }
        });

    }

    private int getItem(int i) { return viewPager.getCurrentItem() + i; }

    private void bottomProgressDots(int current_index) {
        LinearLayout dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        ImageView[] dots = new ImageView[MAX_STEP];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current_index].setImageResource(R.drawable.shape_circle);
            dots[current_index].setColorFilter(getResources().getColor(R.color.light_green_600), PorterDuff.Mode.SRC_IN);
        }
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            bottomProgressDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.item_card_wizard, container, false);
            ((TextView) view.findViewById(R.id.title)).setText(about_title_array[position]);
            ((TextView) view.findViewById(R.id.description)).setText(about_description_array[position]);
            ((ImageView) view.findViewById(R.id.image)).setImageResource(about_images_array[position]);

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return about_title_array.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
