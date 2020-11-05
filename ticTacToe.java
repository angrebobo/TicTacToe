import java.util.Random;
import java.util.Scanner;

/**
 * @author: HuangSiBo
 * @Description:
 * @Data: Created in 13:34 2020/10/15
 */
public class ticTacToe {

    static char[][] board=new char[3][3];   //board是棋盘
    static int flag=-1;     //flag记录最后的赢家，flag==1表示赢家是用户，flag==0表示赢家是电脑


    public static void main(String[] args) {
        //初始化棋盘
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j]='_';     //'_'表示当前坐标上无旗子
            }
        }

        //随机选出先手
        int first;
        if(generator.nextBoolean()){
            System.out.println("User first");
            first=1;
            inputOfUser();
        }
        else {
            System.out.println("Computer first");
            first=0;
            inputOfComputer();
        }


        while(!Check(board)){       //棋盘没有赢家时，一直循环
            printCh();
            if(first==1){
                inputOfComputer();
                printCh();
                inputOfUser();
            }
            else{
                inputOfUser();
                printCh();
                inputOfComputer();
            }

        }

        if(flag==1)
                System.out.println("You win");
        else if(flag==0)
            System.out.println("Computer win");
    }

    /**
     * @params:
     * @description:用户输入
     * @author:
     * @return:
     */
    public static void inputOfUser(){
        Scanner sc=new Scanner(System.in);
        System.out.println("‘x’ is yours,'o' is computer's");
        System.out.println("Enter the position(x,y):"); //让用户输入旗子坐标
        int x= sc.nextInt();int y=sc.nextInt();
        board[x][y]='x';      //用户旗子用‘x’表示
    }

    /**
     * @params:
     * @description:电脑随机生成一个坐标
     * @author:
     * @return:
     */
    public static void inputOfComputer(){
        System.out.println("it's computer's turn");
        Random rm=new Random();    //随机产生一个介于(0,0)到(2,2)的坐标
        int x=rm.nextInt(3);
        int y=rm.nextInt(3);
        while(board[x][y]!='_'){      //如果随机产生的坐标上已经有旗子，再次随机产生一个坐标
            x=rm.nextInt(3);
            y=rm.nextInt(3);
        }
        board[x][y]='o';     //电脑旗子用‘o’表示
        System.out.println("The computer's position is ("+x+" , "+y+")");
    }

    //展现当前的棋盘
    public static void printCh(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
    }

    /**
     *Description:遍历棋盘，检查当前棋盘是否有赢家，有赢家返回true，没有赢家返回false
     *param
     *return
     */
    public static boolean Check(char[][] board){
        boolean getResult=false;
        int numOfx=0;  //记录棋盘中‘x’的个数
        int numOfo=0;  ////记录棋盘中‘o’的个数
        //遍历行
        for (int i = 0; i < 3; i++) {
            numOfo=0;
            numOfx=0;
            for (int j = 0; j < 3; j++) {
                if(board[i][j]=='x')
                    numOfx++;
                else if(board[i][j]=='o')
                    numOfo++;
            }
            if (numOfo==3){
                getResult=true;
                flag=0;   //电脑获胜
            }
            else if(numOfx==3){
                getResult=true;
                flag=1;   //用户获胜
            }
        }

        //遍历列
        if(!getResult){
            numOfo=0;
            numOfx=0;
            for (int i = 0; i < 3; i++) {
                numOfo=0;
                numOfx=0;
                for (int j = 0; j < 3; j++) {
                    if(board[j][i]=='x')
                        numOfx++;
                    else if(board[j][i]=='o')
                        numOfo++;
                }
                if (numOfo==3){
                    getResult=true;
                    flag=0;   //电脑获胜
                }
                else if(numOfx==3){
                    getResult=true;
                    flag=1;   //用户获胜
                }
            }
        }
        //遍历对角线
        if(!getResult){
            numOfo=0;
            numOfx=0;
            for (int i = 0; i < 3; i++){
                if(board[i][i]=='x')
                    numOfx++;
                else if(board[i][i]=='o')
                    numOfo++;
            }
            if (numOfo==3){
                getResult=true;
                flag=0;   //电脑获胜
            }
            else if(numOfx==3){
                getResult=true;
                flag=1;   //用户获胜
            }
        }
        //遍历斜对角线
        if(!getResult){
            numOfo=0;
            numOfx=0;
            for (int i = 0; i < 3; i++){
                if(board[i][2-i]=='x')
                    numOfx++;
                else if(board[i][2-i]=='o')
                    numOfo++;
            }
            if (numOfo==3){
                getResult=true;
                flag=0;   //电脑获胜
            }
            else if(numOfx==3){
                getResult=true;
                flag=1;   //用户获胜
            }
        }
        return getResult;
    }

    public enum Choice{
        Line,          //行
        COLUMN,        //列
        RightDiaonal,  //
        LeftDiagnal;
    }


    private static Random generator = new Random();

}
