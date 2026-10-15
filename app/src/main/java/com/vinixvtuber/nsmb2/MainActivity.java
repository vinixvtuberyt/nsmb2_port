package com.vinixvtuber.nsmb2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String TAG = "NSMB2_Port";
    private TextView debugView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        debugView = new TextView(this);
        debugView.setText("[*] A iniciar o New Super Mario Bros. 2 (Port Android)...\n");
        debugView.append("[+] Mapeamento de controlos ativo: Tátil + Gamepad Bluetooth\n");
        setContentView(debugView);

        try {
            boolean success = CoreBridge.initEngine();
            if (success) {
                debugView.append("[+] Motor BigRed inicializado com sucesso via NDK!\n");
            } else {
                debugView.append("[-] Falha na inicialização do motor.\n");
            }
        } catch (UnsatisfiedLinkError e) {
            debugView.append("[-] Erro ao carregar biblioteca nativa: " + e.getMessage() + "\n");
        }
    }

    // Interceção de botões físicos/Bluetooth (Gamepads e Comandos)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BUTTON_A:
                debugView.append("[Input BT] Botão A premido (Salto)\n");
                return true;
            case KeyEvent.KEYCODE_BUTTON_B:
                debugView.append("[Input BT] Botão B premido (Correr/Bola de Fogo)\n");
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                debugView.append("[Input BT] D-Pad Esquerda\n");
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                debugView.append("[Input BT] D-Pad Direita\n");
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // Interceção de direcionais analógicos Bluetooth
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if ((event.getSource() & MotionEvent.SOURCE_JOYSTICK) == MotionEvent.SOURCE_JOYSTICK) {
            float x = event.getAxisValue(MotionEvent.AXIS_X);
            float y = event.getAxisValue(MotionEvent.AXIS_Y);
            if (Math.abs(x) > 0.2f || Math.abs(y) > 0.2f) {
                Log.d(TAG, "Joystick X: " + x + " Y: " + y);
            }
            return true;
        }
        return super.onGenericMotionEvent(event);
    }
}
