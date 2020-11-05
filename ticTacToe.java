import java.util.Random;
import java.util.Scanner;

/**
 * @author: HuangSiBo
 * @Description:
 * @Data: Created in 13:34 2020/10/15
 */
public class ticTacToe {
    public static void main(String[] args) {
        //初始化棋盘
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '_';     //'_'表示当前坐标上无旗子
            }
        }

        //随机选出先下棋的一方
        int first;
        if( generator.nextBoolean() ){
            System.out.println("User first");
            first = 1;
        }
        else {
            System.out.println("Computer first");
            first = 0;
        }

        //开始下棋
        if(first == 1){
            while (true){
                if(UserPlay() == -1)
                    return;
                if(ComputerPlay() == -1)
                    return;
            }
        }
        else {
            while (true){
                if(ComputerPlay() == -1)
                    return;
                if(UserPlay() == -1)
                    return;
            }
        }
    }

    /**
     * @params:
     * @description:用户输入
     * @author:
     * @return:
     */
    public static int UserPlay(){
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("‘x’是你的棋子,'o'是电脑的棋子 ");
            System.out.println("轮到你了，请输入位置");
            int x = sc.nextInt();
            int y = sc.nextInt();
            if(2<x || x<0 || 2<y || y<0 || board[x][y] != '_'){    //输入的坐标如果越界，重新输入
                System.out.println("输入错误，请重新输入");
                continue;
            }
            board[x][y] = 'x';
            size++;
            break;
        }

        if(isSuccessful() == 1){
            printCh();
            System.out.println("恭喜！！！你赢了");
            return -1;
        }
        else if(size == capacity){
            printCh();
            System.out.println("平局");
            return -1;
        }
        return 0;
    }

    /**
     * @params:
     * @description:电脑下棋
     * @author:
     * @return:
     */
    private static int ComputerPlay(){
        ComputerLogic();
        printCh();
        if(isSuccessful() == -1){
            System.out.println("电脑赢");
            return -1;
        }
        else if(size == capacity){
            System.out.println("平局");
            return -1;
        }
        return 0;
    }

    private static int corner = 2;
    //电脑下棋的逻辑
    public static void ComputerLogic(){
        //电脑逻辑1：抢占中心位置
        if(board[1][1] == '_'){
            board[1][1] = 'o';
            size++;
            return;
        }

        //电脑逻辑2：判断下在当前位置是否能赢
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board[i][j] == '_'){
                    board[i][j] = 'o';
                    if(isSuccessful() == -1){
                        size++;
                        return;
                    }
                    board[i][j] = '_';
                }
            }
        }

        //电脑逻辑3：判断用户下在当前位置是否能赢，能赢的话就占领这个位置
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board[i][j] == '_'){
                    board[i][j] = 'x';
                    if(isSuccessful() == 1){
                        board[i][j] = 'o';
                        size++;
                        return;
                    }
                    board[i][j] = '_';
                }
            }
        }

        //电脑逻辑4：先抢占4个角的位置，如果某一行没有用户的棋子，先抢占这行的角
        if(corner > 0){
            corner--;
            for (int i = 0; i < 3; i++) {
                boolean isBlankLine = true;
                if(i == 1)
                    continue;
                for (int j = 0; j < 3; j++){
                    if(board[i][j] == 'x'){
                        isBlankLine = false;
                    }
                }
                if(isBlankLine){
                    for(int j = 0; j < 3; j++){
                        if(board[i][j] == '_'){
                            board[i][j] = 'o';
                            size++;
                            return;
                        }
                    }
                }
            }
            //每一行都有用户的棋子，那就随机抢占一个角
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(i==1 || j==1)
                        continue;
                    if(board[i][j] == '_'){
                        board[i][j] = 'o';
                        size++;
                        return;
                    }
                }
            }
        }

        //机器逻辑5：判断下在这一步，再下一步能否赢
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board[i][j] == '_'){
                    board[i][j] = 'o';
                    if(willSuccessful(2)){
                        size++;
                        return;
                    }
                    board[i][j] = '_';
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board[i][j] == '_'){
                    board[i][j] = 'x';
                    if(willSuccessful(1)){
                        board[i][j] = 'o';
                        size++;
                        return;
                    }
                    board[i][j] = '_';
                }
            }
        }

        //如果以上几步都失效，随机走
        Random rm = new Random();    //随机产生一个介于(0,0)到(2,2)的坐标
        int x = rm.nextInt(3);
        int y = rm.nextInt(3);
        while( board[x][y] != '_' ){      //如果随机产生的坐标上已经有棋子，再次随机产生一个坐标
            x = rm.nextInt(3);
            y = rm.nextInt(3);
        }
        board[x][y] = 'o';     //电脑棋子用‘o’表示
        size++;
    }

    //展现当前的棋盘
    public static void printCh(){
        System.out.println();
        System.out.println("棋盘:");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    private enum Choice{
        Line,          //行
        COLUMN,        //列
        RightDiagonal, //正对角线
        LeftDiagonal    //副对角线
    }

    /**
     *Description:  计算行，列，正对角线，副对角线是否连成一条线
     *param
     *return
     */
    private static int calculate(Choice choice, int i){
        switch (choice){
            case Line:
                return board[i][0] + board[i][1] + board[i][2];
            case COLUMN:
                return board[0][i] + board[1][i] + board[2][i];
            case RightDiagonal:
                return board[0][0] + board[1][1] + board[2][2];
            case LeftDiagonal:
                return board[0][2] + board[1][1] + board[2][0];
        }
        return 0;
    }

    private static int isSuccessful(){
        /*
          用户赢：’x‘+’x'+'x' == 360，返回1
          电脑赢：'o'+'o'+'o' == 333，返回-1
          继续下棋，返回0
         */
        for (int i = 0; i < 3; i++) {
            if(calculate(Choice.Line,i) == 360 || calculate(Choice.COLUMN,i) == 360)
                return 1;
            if(calculate(Choice.Line,i) == 333 || calculate(Choice.COLUMN,i) == 333)
                return -1;
        }
        if(calculate(Choice.RightDiagonal,0) == 360 || calculate(Choice.LeftDiagonal,0) == 360)
            return 1;
        if(calculate(Choice.RightDiagonal,0) == 333 || calculate(Choice.LeftDiagonal,0) == 333)
            return -1;
        return 0;
    }

    public static boolean willSuccessful(int who){
        /*
           who为1时代表用户，2时代表电脑
          ’x‘+’x'+'_' == 335
          'o'+'o'+'_' == 317
         */
        int n = 0;
        int s = (who == 1) ? 335 : 317;
        for (int i = 0; i < 3; i++) {
                if(calculate(Choice.Line,i) == s)
                    n++;
                if(calculate(Choice.COLUMN,i) == s)
                    n++;
        }
        return  n>1;
    }

    private static Random generator = new Random();
    static char[][] board = new char[3][3];   //board是棋盘
    private static int size =0; //记录已经下了的棋子数
    private static final int capacity = 9; //总的棋子数

}
