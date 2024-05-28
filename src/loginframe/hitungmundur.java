import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.swing.JLabel;

public class HitungMundurThread extends Thread {
    private JLabel countdownLabel;
    private LocalDate tglKembali;

    public HitungMundurThread(JLabel countdownLabel, LocalDate tglKembali) {
        this.countdownLabel = countdownLabel;
        this.tglKembali = tglKembali;
    }

    @Override
    public void run() {
        while (true) {
            LocalDate sekarang = LocalDate.now();
            long hari = ChronoUnit.DAYS.between(sekarang, tglKembali);
            long jam = Duration.between(sekarang.atStartOfDay(), tglKembali.atStartOfDay()).toHours() % 24;
            long menit = Duration.between(sekarang.atStartOfDay(), tglKembali.atStartOfDay()).toMinutes() % 60;
            long detik = Duration.between(sekarang.atStartOfDay(), tglKembali.atStartOfDay()).toMillis() / 1000 % 60;

            String countDownText = String.format("%02d hari %02d jam %02d menit %02d detik", hari, jam, menit, detik);
            countdownLabel.setText(countDownText);

            try {
                Thread.sleep(1000); // Tunggu 1 detik
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
