package com.company;

import java.awt.font.NumericShaper;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static int Size = 3;
    public static int Dots_To_Win = 3;
    public static final char Empt_Dot = '.';
    public static final char X_Dot = 'X';
    public static final char O_Dot = 'O';
    public static char[][] map;
    public static Scanner sc = new Scanner(System.in);

    public static int[][][] rcd;
    private static int Sz;
    private static void InitMap(){

        map = new char[Size][Size];
        for(int i = 0; i < Size;i++)
            for(int j = 0; j < Size;j++)
                map[i][j] = Empt_Dot;

        rcd = new int[Size][Size][4];
        for(int i = 0; i < Size;i++)
            for(int j = 0; j < Size;j++)
                for(int z = 0;z < 4;z++)
                    rcd[i][j][z] = 0;
        Sz = Size*Size;
    }
    private static void RenderMap(){
        for(int i = 0; i < Size;i++)
            System.out.println(Arrays.toString(map[i]));
    }

    private static boolean IsCellValid(int x,int y, char c){
        if(!(  (x < Size) && (y < Size) && (x >= 0) && (y >= 0)))
            return false;
        else if(map[x][y] == c)
            return true;

        return false;
    }
    private static void ChangeRCD(int x,int y){
        int c = 1;
        int r = 1;
        int m_d = 1;
        int s_d = 1;
        for(int n = -Dots_To_Win+1; n < Dots_To_Win;n++){
            if(IsCellValid(x,y+n,Empt_Dot))
                rcd[x][y+n][0]+= c;
            else if(IsCellValid(x,y+n,O_Dot))
                c = 0;
            if(IsCellValid(x+n,y,Empt_Dot))
                rcd[x+n][y][1]+= r;
            else if(IsCellValid(x+n,y,O_Dot))
                r = 0;
            if(IsCellValid(x+n,y+n,Empt_Dot))
                rcd[x+n][y+n][2]+= m_d;
            else if(IsCellValid(x+n,y+n,O_Dot))
                m_d = 0;
            if(IsCellValid(x+n,y-n,Empt_Dot))
                rcd[x+n][y-n][3]+= s_d;
            else if(IsCellValid(x+n,y-n,O_Dot))
                s_d = 0;
        }
    }
    private static void ChangeRCDatPoint(int x,int y){
        int c = 1;
        int r = 1;
        int m_d = 1;
        int s_d = 1;

        if(IsCellValid(x,y,'.')) {
            for(int i = 0;i < rcd[x][y].length;i++)
                rcd[x][y][i] = 0;
        }
        else return;

        for(int n = -Dots_To_Win+1; n < Dots_To_Win;n++){
            if(IsCellValid(x,y+n,Empt_Dot)){
                if(map[x][y+n] == X_Dot)
                    rcd[x][y][0]+= c;}
            else if(IsCellValid(x,y+n,O_Dot))
                c = 0;
            if(IsCellValid(x+n,y,Empt_Dot)){
                if(map[x+n][y] == X_Dot)
                    rcd[x][y][1]+= r;}
            else if(IsCellValid(x+n,y,O_Dot))
                r = 0;
            if(IsCellValid(x+n,y+n,Empt_Dot)){
                if(map[x+n][y+n] == X_Dot)
                    rcd[x][y][2]+= m_d;}
            else if(IsCellValid(x+n,y+n,O_Dot))
                m_d = 0;
            if(IsCellValid(x+n,y-n,Empt_Dot)){
                if(map[x+n][y-n] == X_Dot)
                    rcd[x][y][3]+= s_d;}
            else if(IsCellValid(x+n,y-n,O_Dot))
                s_d = 0;
        }
    }
    private static void HumanTurn(){
        System.out.println("Human Turn...");
        int x,y;
        do {
            System.out.println("X:... Y:...");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        }while(!IsCellValid(x,y,Empt_Dot));
        map[x][y] = X_Dot;
        ChangeRCD(x,y);
        Sz--;
    }

    private static int _Max(int[] m){
        if(m.length == 0)
            return -1;
        int max = m[0];
        for(int j : m)
            if(j > max)
                max = j;
        return max;
    }

    private static void AiTurn(){
        System.out.println("Ai turn...");
        Stack<int[]> s = new Stack<>();
        int max = -1;
        int m_x = 0;
        int m_y = 0;
        int[] buff = new int[5];
        for(int i = 0; i < Size;i++) {
            for(int j = 0;j < Size;j++){
                if(IsCellValid(i,j,Empt_Dot))
                {
                    if(_Max(rcd[i][j]) > max) {
                        max = _Max(rcd[i][j]);
                        m_x = i;
                        m_y = j;
                        s.clear();
                        for(int z = 0; z < 4;z++)
                            buff[z] = rcd[i][j][z];
                        buff[4] = m_x*Size + m_y;
                        s.add(buff);
                    }
                    else if(_Max(rcd[i][j]) == max){
                        for(int z = 0; z < 4;z++)
                            buff[z] = rcd[i][j][z];
                        buff[4] = m_x*Size + m_y;
                        s.add(buff);
                    }
                }
            }
        }
        if(max != -1){
            int b_max = -1;
            for(int[] it : s)
            {
                int lc = it[0]+it[1]+it[2]+it[3];
                if(lc > b_max) {
                    b_max = lc;
                    m_x = it[4]/Size;
                    m_y = it[4] % Size;
                }
            }
            Sz--;
            map[m_x][m_y] = O_Dot;
            for(int n = -Dots_To_Win+1; n < Dots_To_Win;n++){
                ChangeRCDatPoint(m_x,m_y+n);
                ChangeRCDatPoint(m_x+n,m_y);
                ChangeRCDatPoint(m_x+n,m_y+n);
                ChangeRCDatPoint(m_x+n,m_y-n);
            }
        }
    };

    private static boolean WinCheckAtPoint(int x,int y,char Win_Dot){
        boolean c = true;
        boolean r = true;
        boolean m_d = true;
        boolean s_d = true;
        for(int n = 0; n < Dots_To_Win;n++) {
            if (!IsCellValid(x, y + n, Win_Dot))
                c = false;
            if (!IsCellValid(x + n, y, Win_Dot))
                r = false;
            if (!IsCellValid(x + n, y + n, Win_Dot))
                m_d = false;
            if (!IsCellValid(x + n, y - n, Win_Dot))
                s_d = false;
        }
        return c || r || m_d || s_d;
    };
    private static boolean WinCheck(char Win_Dot){
        for(int i = 0; i < Size;i++)
            for(int j = 0;j < Size;j++)
                if(WinCheckAtPoint(i,j,Win_Dot))
                    return true;
        return false;
    }
    public static void main(String[] args) {
        InitMap();
        RenderMap();
        while(true)
        {
            HumanTurn();
            RenderMap();
            if(WinCheck(X_Dot)){
                System.out.println("You won");
                break;
            }
            if(Sz == 0){
                System.out.println("Draw");
                break;
            }
            AiTurn();
            RenderMap();
            if(WinCheck(O_Dot)){
                System.out.println("Ai won");
                break;
            }
            if(Sz == 0){
                System.out.println("Draw");
                break;
            }
        }
    }
}
