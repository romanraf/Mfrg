import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class NumButton extends JButton{
    public NumButton(int num){
        super(Integer.toString(num));
        value = num;
    }
    public int value;
}

public class mn extends JFrame {
    private boolean firstPush = true;
    private int ind_1, ind_2;

    private int pairsNum = 0;
    private NumButton[] Jbs;
    private ActionListener Checker;
    private void InitNums()
    {
        pairsNum = 0;
        Random rand;
        rand = new Random();
        Jbs = new NumButton[9];
        for(int i = 0;i < Jbs.length;i++) {
            Jbs[i] = new NumButton(rand.nextInt(10));
            for(int j = 0;j < i;j++)
                if(Jbs[i].value == Jbs[j].value)
                    pairsNum++;
        }
    }
    private void InitGrid()
    {
        int col = (int) Math.sqrt(Jbs.length);
        int row = Jbs.length / col;
        if(row*col < Jbs.length)
            row++;

        setLayout(new GridLayout(row,col));

        for(int i = 0;i < Jbs.length;i++)
            add(Jbs[i]);
    }
    private void InitChecker()
    {
        Checker = new ActionListener() {
            NumButton btn_1;
            boolean firstPush = true;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (firstPush == true) {
                    btn_1 = (NumButton) e.getSource();
                    firstPush = false;
                    btn_1.setEnabled(false);
                }
                else{
                    if(btn_1.value == ((NumButton) e.getSource()).value) {
                        ((NumButton) e.getSource()).setEnabled(false);
                        pairsNum--;
                        if(pairsNum <= 0)
                            System.exit(1);
                    }
                    else
                        btn_1.setEnabled(true);

                    firstPush = true;
                }
            }
        };
        for(int i = 0;i < Jbs.length;i++) {
            int ind_strd = i;
            Jbs[i].addActionListener(Checker);
        }
    }

    public mn(int initialValue) {
        setBounds(500, 500, 400, 400);
        setTitle("mn");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Font font = new Font("Arial", Font.BOLD, 32);

        InitNums();
        InitGrid();
        InitChecker();

        setVisible(true);
    }

    public static void main(String[] args) {
        new mn(0);
    }
}


