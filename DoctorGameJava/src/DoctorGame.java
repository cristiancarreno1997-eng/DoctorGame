import javax.swing.*;
import java.awt.*;
import java.io.File;

public class DoctorGame extends JPanel {

    private Image[] babies;
    private Image doctorImage;
    private int currentBaby = 0;
    private int level = 0;

    private String message = "";
    private String currentQuestion = "";
    private String correctTool = "";

    private String[] questions = {
        "El bebe tiene fiebre. Que necesita?",
        "Al bebe le duele la garganta. Que necesitas usar?",
        "El bebe tiene una herida. Que necesita?",
        "El bebe tiene tos. Que necesita?"
    };

    private String[] answers = {
        "thermo",
        "stetho",
        "bandage",
        "medicine"
    };

    public DoctorGame() {
        setLayout(null);
        setBackground(Color.WHITE);

        babies = new Image[] {
            loadImage("assets/baby.png"),
            loadImage("assets/baby_kirby.png"),
            loadImage("assets/baby_mario.png"),
            loadImage("assets/baby_zelda.png")
        };

        doctorImage = loadImage("assets/doctor.png");

        loadLevel();

        add(createToolButton("assets/icons/icon_stethoscope.png", 80, 420, "stetho"));
        add(createToolButton("assets/icons/icon_thermometer.png", 160, 420, "thermo"));
        add(createToolButton("assets/icons/icon_medicine.png", 240, 420, "medicine"));
        add(createToolButton("assets/icons/icon_bandage.png", 320, 420, "bandage"));
    }

    private void loadLevel() {
        if (level >= questions.length) {
            currentQuestion = "Todos los bebes estan curados!";
            message = "Buen trabajo.";
            repaint();
            return;
        }

        currentQuestion = questions[level];
        correctTool = answers[level];
        message = "Elige la herramienta correcta";
        repaint();
    }

    private JButton createToolButton(String path, int x, int y, String id) {
        JButton btn = new JButton(new ImageIcon(path));
        btn.setBounds(x, y, 64, 64);
        btn.setFocusPainted(false);

        btn.addActionListener(e -> {
            if (id.equals(correctTool)) {
                message = "Correcto.";

                currentBaby = (currentBaby + 1) % babies.length;
                if (babies[currentBaby] == null) {
                    currentBaby = 0;
                }

                level++;
                loadLevel();
            } else {
                message = "No era eso.";
                repaint();
            }
        });

        return btn;
    }

    private Image loadImage(String path) {
        File f = new File(path);
        if (f.exists()) {
            return new ImageIcon(path).getImage();
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Doctor Game - Educativo", 20, 30);

        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString(currentQuestion, 20, 70);

        if (doctorImage != null) {
            g.drawImage(doctorImage, 40, 140, 150, 150, this);
        }

        if (babies[currentBaby] != null) {
            g.drawImage(babies[currentBaby], 360, 160, 200, 200, this);
        }

        g.drawString(message, 260, 380);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Doctor Game");
        frame.setSize(800, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.add(new DoctorGame());
        frame.setVisible(true);
    }
}
