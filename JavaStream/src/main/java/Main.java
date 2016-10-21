import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Main {

    /**
     * Streamの検証の際、デバッグ文で URL と シーケンス番号 を表示するために用意したクラス
     * URL から Image を作成するメソッド getImage() を実装する。
     */
    private static class ImageUrl {
        private int seqNo;
        private URL url;

        public ImageUrl(int seqNo, URL url) {
            this.seqNo = seqNo;
            this.url = url;
        }

        public ImageWithSeqNo getImage() {
            System.out.println(this.seqNo + " : START : " + this.url.toString());
            Image image = null;
            try {
                Object content = this.url.getContent();
                if (content instanceof ImageProducer) {
                    ImageProducer imageProducer = (ImageProducer)content;
                    image = Toolkit.getDefaultToolkit().createImage(imageProducer);
                }
            } catch (IOException e) {
                image = null;
            }
            System.out.println(this.seqNo + " : END   : " + this.url.toString());
            return new ImageWithSeqNo(this.seqNo, Optional.ofNullable(image));
        }
    }

    /**
     * Streamの検証の際、デバッグ文で Image と シーケンス番号 を表示するために用意したクラス
     */
    private static class ImageWithSeqNo {
        private int seqNo;
        private Optional<Image> imageOptional;

        public ImageWithSeqNo(int seqNo, Optional<Image> imageOptional) {
            this.seqNo = seqNo;
            this.imageOptional = imageOptional;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.seqNo);
            stringBuilder.append(" : ");
            if (this.imageOptional.isPresent()) {
                stringBuilder.append("get image");
            } else {
                stringBuilder.append("ERROR");
            }
            return stringBuilder.toString();
        }
    }

    public static void main(String[] args) {
        List<ImageUrl> urlList = new ArrayList<>();
        try {
            urlList.add(new ImageUrl(1, new URL("http://www.nasa.gov/images/content/178937main_07pd1393.jpg")));
            urlList.add(new ImageUrl(2, new URL("http://img.kakaku.com/images/magazine/report/029/img35_l.jpg")));
            urlList.add(new ImageUrl(3, new URL("http://www.tomytec.co.jp/borg/world/blog/20121202-71FL%2BGR14x%2BEF2x%2BSD1_SDI0854_x2_2400pix.JPG")));
            urlList.add(new ImageUrl(4, new URL("http://www.nasa.gov/images/content/665600main_pia15689-full2_full.jpg")));
            urlList.add(new ImageUrl(5, new URL("http://kabegami.halfmoon.jp/landscape/alps/image/fuji05.jpg")));
            urlList.add(new ImageUrl(6, new URL("http://imgs.ttwalls.com/images/Abstract/34pmowpu2v0.jpg")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        parallelByStream(urlList);
        notParallelByStream(urlList);
        parallelByStream(urlList);
        notParallelByStream(urlList);
        parallelByStream(urlList);
        notParallelByStream(urlList);
    }

    private static void notParallelByStream(List<ImageUrl> urlList) {
        System.out.println("notParallelByStream");
        Date startDate = new Date();

        // stream() を指定することで 1つずつ順番に 処理をする。
        Object[] imageArray = urlList.stream().map(imageUrl -> {
            return imageUrl.getImage();
        }).toArray();

        Date endDate = new Date();
        System.out.println("処理時間： " + (endDate.getTime() - startDate.getTime()) + "ミリ秒");
        Arrays.stream(imageArray).forEach(image -> System.out.println(image.toString()));
        System.out.println();
    }

    private static void parallelByStream(List<ImageUrl> urlList) {
        System.out.println("parallelByStream");
        Date startDate = new Date();

        // parallelStream() を指定することで 可能であれば複数同時に 処理をする。
        Object[] imageArray = urlList.parallelStream().map(imageUrl -> {
            return imageUrl.getImage();
        }).toArray();

        Date endDate = new Date();
        System.out.println("処理時間： " + (endDate.getTime() - startDate.getTime()) + "ミリ秒");
        Arrays.stream(imageArray).forEach(image -> System.out.println(image.toString()));
        System.out.println();
    }

}
