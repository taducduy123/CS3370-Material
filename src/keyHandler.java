import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;

public class keyHandler implements KeyListener {

    DemoPanel dp;

//--------------------------

    public keyHandler(DemoPanel dp){
        this.dp = dp;
    }


    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_ENTER){
            System.out.println(dp.search());
            //System.out.println(dp.AutoSearch());
            //System.out.println("You are pressing enter key");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
