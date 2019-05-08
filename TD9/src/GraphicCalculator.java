import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class GraphicCalculator extends Application {
	Calculator calc = new Calculator();
	String s = "123+456-789*0.C/()$=";
	String scalc = "0.0";
	boolean flag = true;
	Label label = new Label("0.0");
	final int buttonSize = 30;

	public Button b(char c) {
		Button button = new Button("" + c);
		button.setMinSize(40, 30);
		button.setMaxSize(40, 30);

		return button;
	}

	@Override
	public void start(Stage stage)  throws ParsingException{
		stage.setTitle("Nino sama");
		stage.setHeight(220);
		stage.setWidth(180);
		stage.show();
		
		label.setFont(new Font("Arial", 12));
		HBox hbox = new HBox();
		hbox.setPrefSize(30, 30);
		hbox.getChildren().add(label);
		VBox vbox = new VBox(hbox);
		
		HBox[] h = new HBox[5];
		HBox htemp = new HBox();
		VBox vtemp1 = new VBox(), vtemp2 = new VBox();
		vtemp2.setSpacing(10);
		
		Button[] button = new Button[20];
//		Button btemp = new Button();
		for(int i = 0; i != 20; ++i) 
			{
				button[i] = b(s.charAt(i));
//				button[i].setOnAction(value -> {
//					try {
//						System.out.println(((Button)value.getSource()).getText());
//						update(((Button)value.getSource()).getText().charAt(0));
//						label.setText("p");
//					} catch (ParsingException e) {
//						 TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					button[i].setOnAction(new EventHandler<ActionEvent>() {
			            @Override
			            public void handle(ActionEvent event) {
			            	try {
								update(((Button)event.getSource()).getText().charAt(0));
							} catch (ParsingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
//			                System.out.println("Hello World!");
			            }
			        });
//				});
			}

	for(

	int i = 0;i!=5;++i)
	{
		h[i] = new HBox();
		h[i].setSpacing(10);
		htemp = new HBox();
		for (int j = 0; j != 4; ++j) {
			if (j < 3)
				htemp.getChildren().add(button[i * 4 + j]);
			else {
				h[i].getChildren().add(htemp);
				h[i].getChildren().add(new HBox(button[i * 4 + j]));
			}
		}
		if (i < 4)
			vtemp1.getChildren().add(h[i]);
		else {
			vtemp2.getChildren().add(vtemp1);
			vtemp2.getChildren().add(h[4]);
		}
	}vbox.getChildren().add(vtemp2);
	label.setText(scalc);
	
	Scene scene = new Scene(vbox);
	
	scene.setOnKeyTyped(e -> handlekey(e));
   	stage.setScene(scene);
	}

	public void handlekey(KeyEvent e) {
		if(s.contains(e.getCharacter())) {
			try {
				update(e.getCharacter().charAt(0));
			} catch (ParsingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void update(char c) throws ParsingException {
		if (((c >= '0') && (c <= '9')) || (c == '.')) {
			if(flag == true) {
				calc.read('C');
				scalc = "" + c;
				}
			else scalc = scalc + c;
			
			flag = false;
			label.setText(scalc);
			System.out.println(scalc);
			return;
		}
		
		if(c == '='){
			flag = true;
			scalc = scalc + '=';
			calc.read(scalc);
			scalc = calc.numbers.peek().toString();
			label.setText(scalc);
			System.out.println(calc);
			System.out.println(calc.results);
		}else if(c == 'C') {
			
			scalc = "0.0";
			flag = true;
			label.setText("0.0");
			calc.read('C');
		}else if(calc.isOperator(c)) {
			if(flag && (c == '(' || c == '$')){
				scalc = "";
			}
			flag = false;
			scalc = scalc + c;
			label.setText(scalc);
		}
		System.out.println(scalc);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
