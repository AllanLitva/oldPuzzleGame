import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class SliderAppModel {

    public String[][]  pictures;


    public SliderAppModel(String[][] pictures){
        pictures = pictures;
    }
    public void randomize(String[][] input){
        for(int row = 0; row<input.length;row++) {
            for (int col = 0; col < input[row].length; col++) {
                int indexOne = (int) (Math.random() * input.length);
                int indexTwo = (int) (Math.random() * input[row].length);

                String temp = input[row][col];
                input[row][col] = input[indexOne][indexTwo];
                input[indexOne][indexTwo] = temp;
            }
        }

    }
    public void formButtons(String[][]input,Button[][] buttons){
        for(int row=0; row<4; row++){
            for(int col=0; col<4; col++){
                buttons[row][col].setGraphic(new ImageView(new Image(getClass().
                        getResourceAsStream(input[row][col]))));
            }
        }
    }
    public int[]  setRandomBlank(String[][] input) {
        int[] result = new int[2];
        for (int row = 0; row < 1; row++) {
            for (int col = 0; col < 1; col++) {
                int indexOne = (int) (Math.random() * input.length);
                int indexTwo = (int) (Math.random() * input[col].length);
                result[0] = indexOne;
                result[1] = indexTwo;
                input[indexOne][indexTwo] = "Blank.png";


            }
        }
        return result;
    }
    // this checks the puzzle to see if 15 of the positions are in the right place( the other piece is blank)
    public boolean checkPuzzle(String[][] buttonLabelArray){
        int counter=0;

        String[][] CompletedPuzzle = {

                {"Lego_00.png","Lego_01.png","Lego_02.png","Lego_03.png"}
                ,{"Lego_10.png","Lego_11.png", "Lego_12.png","Lego_13.png"}
                ,{"Lego_20.png","Lego_21.png", "Lego_22.png","Lego_23.png"},
                {"Lego_30.png","Lego_31.png","Lego_32.png","Lego_33.png"}};
        for (int row = 0; row < buttonLabelArray.length; row++) {
            for (int col = 0; col < buttonLabelArray.length; col++) {
                if (buttonLabelArray[row][col].equals(CompletedPuzzle[row][col])){

                    counter+=1;

                }
            }

        }
        if (counter==15){
            return true;

        }
        else
            return false;




    }
    public void endGame(Button[][] buttons){
        for(int row=0; row<4; row++){
            for(int col=0; col<4; col++){
                buttons[row][col].setDisable(true);
            }
        }
    }


}
