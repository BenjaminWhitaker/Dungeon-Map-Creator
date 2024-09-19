import javax.swing.JFrame;

public class DungeonRunner {

public static void main(String[] args) {
        JFrame f = new JFrame("Dungeon"); 
        DungeonPanel p = new DungeonPanel();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(p);
        f.pack();
        f.setVisible(true);
        p.setFocusable(true);
        p.requestFocusInWindow();
        p.run(); 
    }
}
