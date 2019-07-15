package application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class ProzorController {

	BufferedImage bf = null;
	int rArr[] = new int[256];
	int gArr[] = new int[256];
	int bArr[] = new int[256];
	String imageName;
	
	@FXML
	TextField imagePath;
	@FXML
	Button button;
	@FXML
	ImageView slika;
	@FXML
	ImageView slikaR;
	@FXML
	ImageView slikaG;
	@FXML
	ImageView slikaB;
	@FXML
	ImageView adjustedSlikaR;
	@FXML
	ImageView adjustedSlikaG;
	@FXML
	ImageView adjustedSlikaB;
	@FXML
	Slider sliderR;
	@FXML
	Slider sliderG;
	@FXML
	Slider sliderB;
	@FXML
	Pane rPane;
	@FXML
	Line rLine;
	@FXML
	Pane gPane;
	@FXML
	Line gLine;
	@FXML
	Pane bPane;
	@FXML
	Line bLine;
	@FXML
	ImageView otsuR;
	@FXML
	ImageView otsuG;
	@FXML
	ImageView otsuB;

	@FXML
	private BarChart<String, Number> rChart;

	@FXML
	private BarChart<String, Number> gChart;

	@FXML
	private BarChart<String, Number> bChart;

// SLIDERI ZA RUČNO NAMJEŠTANJE TRESHOLDA
//	public void initialize() {
//		sliderR.valueProperty().addListener((observable, oldValue, newValue) -> {
//
//			onMoveSliderR(newValue.intValue());
//		});
//		sliderG.valueProperty().addListener((observable, oldValue, newValue) -> {
//
//			onMoveSliderG(newValue.intValue());
//		});
//		sliderB.valueProperty().addListener((observable, oldValue, newValue) -> {
//
//			onMoveSliderB(newValue.intValue());
//		});
//	}
//
//	public void onMoveSliderR(int sliderValue) {
//		WritableImage wr = null;
//		if (bf != null) {
//			wr = new WritableImage(bf.getWidth(), bf.getHeight());
//			PixelWriter pw = wr.getPixelWriter();
//			for (int x = 0; x < bf.getWidth(); x++) {
//				for (int y = 0; y < bf.getHeight(); y++) {
//
//					Color awtColor = new Color(bf.getRGB(x, y));
//					int r = awtColor.getRed();
//
//					if (r < sliderValue)
//						pw.setColor(x, y, javafx.scene.paint.Color.BLACK);
//					else
//						pw.setColor(x, y, javafx.scene.paint.Color.WHITE);
//				}
//			}
//		}
//		adjustedSlikaR.setImage(wr);
//	}
//
//	public void onMoveSliderG(int sliderValue) {
//		WritableImage wr = null;
//		if (bf != null) {
//			wr = new WritableImage(bf.getWidth(), bf.getHeight());
//			PixelWriter pw = wr.getPixelWriter();
//			for (int x = 0; x < bf.getWidth(); x++) {
//				for (int y = 0; y < bf.getHeight(); y++) {
//
//					Color awtColor = new Color(bf.getRGB(x, y));
//					int r = awtColor.getGreen();
//
//					if (r < sliderValue)
//						pw.setColor(x, y, javafx.scene.paint.Color.BLACK);
//					else
//						pw.setColor(x, y, javafx.scene.paint.Color.WHITE);
//				}
//			}
//		}
//		adjustedSlikaG.setImage(wr);
//	}
//
//	public void onMoveSliderB(int sliderValue) {
//		WritableImage wr = null;
//		if (bf != null) {
//			wr = new WritableImage(bf.getWidth(), bf.getHeight());
//			PixelWriter pw = wr.getPixelWriter();800
//			for (int x = 0; x < bf.getWidth(); x++) {
//				for (int y = 0; y < bf.getHeight(); y++) {
//
//					Color awtColor = new Color(bf.getRGB(x, y));
//					int r = awtColor.getBlue();
//
//					if (r < sliderValue)
//						pw.setColor(x, y, javafx.scene.paint.Color.BLACK);
//					else
//						pw.setColor(x, y, javafx.scene.paint.Color.WHITE);
//				}
//			}
//		}
//		adjustedSlikaB.setImage(wr);
//	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void onClickBtn() {

		bf = null;
		try {
			bf = ImageIO.read(new File(imagePath.getText()));
			imageName = imagePath.getText().toString();
		} catch (IOException ex) {
			System.out.println("Image failed to load.");
		}

		WritableImage wr = null;
		if (bf != null) {
			wr = new WritableImage(bf.getWidth(), bf.getHeight());
			PixelWriter pw = wr.getPixelWriter();
			for (int x = 0; x < bf.getWidth(); x++) {
				for (int y = 0; y < bf.getHeight(); y++) {
					pw.setArgb(x, y, bf.getRGB(x, y));
				}
			}
		}

		slika.setImage(wr);

		if (bf != null) {
			wr = new WritableImage(bf.getWidth(), bf.getHeight());
			PixelWriter pw = wr.getPixelWriter();
			for (int x = 0; x < bf.getWidth(); x++) {
				for (int y = 0; y < bf.getHeight(); y++) {

					Color c = new Color(bf.getRGB(x, y));
					int r = c.getRed();
					rArr[r] += 1;

					pw.setArgb(x, y, new Color(r, r, r, c.getAlpha()).getRGB());

				}
			}
		}
		slikaR.setImage(wr);

		if (bf != null) {
			wr = new WritableImage(bf.getWidth(), bf.getHeight());
			PixelWriter pw = wr.getPixelWriter();
			for (int x = 0; x < bf.getWidth(); x++) {
				for (int y = 0; y < bf.getHeight(); y++) {

					Color c = new Color(bf.getRGB(x, y));
					int r = c.getGreen();
					gArr[r] += 1;

					pw.setArgb(x, y, new Color(r, r, r, c.getAlpha()).getRGB());
				}
			}
		}

		slikaG.setImage(wr);
		if (bf != null) {
			wr = new WritableImage(bf.getWidth(), bf.getHeight());
			PixelWriter pw = wr.getPixelWriter();
			for (int x = 0; x < bf.getWidth(); x++) {
				for (int y = 0; y < bf.getHeight(); y++) {
					Color c = new Color(bf.getRGB(x, y));
					int r = c.getBlue();
					bArr[r] += 1;

					pw.setArgb(x, y, new Color(r, r, r,c.getAlpha()).getRGB());
				}
			}
		}

		slikaB.setImage(wr);
		XYChart.Series series1 = new XYChart.Series();
		XYChart.Series series2 = new XYChart.Series();
		XYChart.Series series3 = new XYChart.Series();
		for (Integer i = 0; i < 256; i++) {
			series1.getData().add(new XYChart.Data("" + i, rArr[i]));
			series2.getData().add(new XYChart.Data("" + i, gArr[i]));
			series3.getData().add(new XYChart.Data("" + i, bArr[i]));
		}
		rChart.getData().addAll(series1);
		gChart.getData().addAll(series2);
		bChart.getData().addAll(series3);

		// racunanje outsu algoritma za sve tri slike i binarizacija
		int maxTr = outsu(rArr);
		int maxTg = outsu(gArr);
		int maxTb = outsu(bArr);
		System.out.println(maxTr);
		System.out.println(maxTg);
		System.out.println(maxTb);
		binarizacijR(maxTr);
		binarizacijG(maxTg);
		binarizacijB(maxTb);

		// kod vezan uz vertikalnu liniju
		rChart.boundsInLocalProperty().addListener(new ChangeListener<Bounds>() {
			@Override
			public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
				Platform.runLater(() -> {
					updateLines(maxTr, maxTg, maxTb);

				});
			}
		});
		gChart.boundsInLocalProperty().addListener(new ChangeListener<Bounds>() {
			@Override
			public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
				Platform.runLater(() -> {
					updateLines(maxTr, maxTg, maxTb);
				});
			}
		});
		bChart.boundsInLocalProperty().addListener(new ChangeListener<Bounds>() {
			@Override
			public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
				Platform.runLater(() -> {
					updateLines(maxTr, maxTg, maxTb);
				});
			}
		});
		updateLines(maxTr, maxTg, maxTb);

		
		
	}

	// postavljanje linije

	private void updateLines(Integer tr, Integer tg, Integer tb) {
		Node rChartArea = rChart.lookup(".chart-plot-background");
		Bounds rChartAreaBounds = rChartArea.localToParent(rChartArea.getBoundsInLocal());
		Node gChartArea = gChart.lookup(".chart-plot-background");
		Bounds gChartAreaBounds = gChartArea.localToParent(gChartArea.getBoundsInLocal());
		Node bChartArea = bChart.lookup(".chart-plot-background");
		Bounds bChartAreaBounds = bChartArea.localToParent(bChartArea.getBoundsInLocal());
		
		
		double xShift = rChartAreaBounds.getMinX();
		final double rPos = rChart.getXAxis().getDisplayPosition(tr.toString());
		rLine.setStartX(rPos+xShift);
		rLine.setEndX(rPos+xShift);
		final double gPos = gChart.getXAxis().getDisplayPosition(tg.toString());
		xShift = gChartAreaBounds.getMinX();
		gLine.setStartX(gPos+xShift);
		gLine.setEndX(gPos+xShift);
		final double bPos = bChart.getXAxis().getDisplayPosition(tb.toString());
		xShift = bChartAreaBounds.getMinX();
		bLine.setStartX(bPos+xShift);
		bLine.setEndX(bPos+xShift);
	}
	// otsu algoritam
	public int outsu(int[] array) {
		double maxV = 0;
		int maxT = 0;
		double brojPiksela = 0;
		for (int i = 0; i < array.length; i++) {
			brojPiksela += array[i];
		}
		for (int T = 0; T < 256; T++) {
			double w1 = 0, u1 = 0, u2 = 0;
			double w1Broj = 0;
			double brojRazinaSivog = 0;
			double u1Broj = 0;
			double u2Broj = 0;

			for (int i = 0; i < T; i++) {
				w1Broj += array[i];
				brojRazinaSivog += array[i];
				u1Broj += i * array[i];
			}

			w1 = w1Broj / brojPiksela;
			u1 = u1Broj / brojRazinaSivog;
			brojRazinaSivog = 0;
			for (int i = T; i < 256; i++) {
				u2Broj += i * array[i];
				brojRazinaSivog += array[i];
			}
			u2 = u2Broj / brojRazinaSivog;
			double tempMax = w1 * (1 - w1) * (u1 - u2) * (u1 - u2);
			if (tempMax > maxV) {
				maxV = tempMax;
				maxT = T;
			}
		}
		return maxT;
	}

	public void binarizacijR(int T) {
		WritableImage wr = null;
		if (bf != null) {
			wr = new WritableImage(bf.getWidth(), bf.getHeight());
			PixelWriter pw = wr.getPixelWriter();
			for (int x = 0; x < bf.getWidth(); x++) {
				for (int y = 0; y < bf.getHeight(); y++) {

					Color awtColor = new Color(bf.getRGB(x, y));
					int r = awtColor.getRed();

					if (r < T)
						pw.setColor(x, y, javafx.scene.paint.Color.BLACK);
					else
						pw.setColor(x, y, javafx.scene.paint.Color.WHITE);
				}
			}
		}
		otsuR.setImage(wr);
		String nativeDir = imageName.substring(0, imageName.lastIndexOf("/"));
		System.out.println(nativeDir + "/BinariziranaCrvena.png");
		File file = new File(nativeDir + "/BinariziranaCrvena.png");
		RenderedImage renderedImage = SwingFXUtils.fromFXImage(wr, null);
		try {
			ImageIO.write(
			        renderedImage, 
			        "png",
			        file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void binarizacijG(int T) {
		WritableImage wr = null;
		if (bf != null) {
			wr = new WritableImage(bf.getWidth(), bf.getHeight());
			PixelWriter pw = wr.getPixelWriter();
			
			for (int x = 0; x < bf.getWidth(); x++) {
				for (int y = 0; y < bf.getHeight(); y++) {

					Color awtColor = new Color(bf.getRGB(x, y));
					int r = awtColor.getGreen();

					if (r < T)
						pw.setColor(x, y, javafx.scene.paint.Color.BLACK);
					else
						pw.setColor(x, y, javafx.scene.paint.Color.WHITE);
				}
			}
		}
		otsuG.setImage(wr);
		String nativeDir = imageName.substring(0, imageName.lastIndexOf("/"));
		System.out.println(nativeDir + "/BinariziranaZelena.png");
		File file = new File(nativeDir + "/BinariziranaZelena.png");
		RenderedImage renderedImage = SwingFXUtils.fromFXImage(wr, null);
		try {
			ImageIO.write(
			        renderedImage, 
			        "png",
			        file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void binarizacijB(int T) {
		WritableImage wr = null;
		if (bf != null) {
			wr = new WritableImage(bf.getWidth(), bf.getHeight());
			PixelWriter pw = wr.getPixelWriter();
			for (int x = 0; x < bf.getWidth(); x++) {
				for (int y = 0; y < bf.getHeight(); y++) {

					Color awtColor = new Color(bf.getRGB(x, y));
					int r = awtColor.getBlue();

					if (r < T)
						pw.setColor(x, y, javafx.scene.paint.Color.BLACK);
					else
						pw.setColor(x, y, javafx.scene.paint.Color.WHITE);
				}
			}
		}
		otsuB.setImage(wr);
		// /home/pero/Desktop/ja.jpg
		String nativeDir = imageName.substring(0, imageName.lastIndexOf("/"));
		System.out.println(nativeDir + "/BinariziranaPlava.png");
		File file = new File(nativeDir + "/BinariziranaPlava.png");
		RenderedImage renderedImage = SwingFXUtils.fromFXImage(wr, null);
		try {
			ImageIO.write(
			        renderedImage, 
			        "png",
			        file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}