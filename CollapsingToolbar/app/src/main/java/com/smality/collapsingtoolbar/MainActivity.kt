package com.smality.collapsingtoolbar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.zIndex
import com.smality.collapsingtoolbar.ui.theme.CollapsingToolbarTheme

val AppBarExpandedHeight: Dp = 350.dp
val AppBarCollapsedHeight: Dp = 60.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CollapsingToolbarTheme {
                Surface(color=MaterialTheme.colors.surface) {
                    MainFragment()
                }
            }
        }
    }
}

@Composable
fun MainFragment() {
    val scrollState = rememberLazyListState()

    Box {
        Content(scrollState)
        ParallaxToolbar(scrollState)
        ToolbarActions(Modifier.zIndex(4f))
    }
}

@Composable
fun ParallaxToolbar(scrollState: LazyListState) {
    val imageHeight = AppBarExpandedHeight - AppBarCollapsedHeight
    //Dp olan resim yükseklik değerini (imageHeight) int değere dönüştürüyoruz
    val maxOffset = with(LocalDensity.current) { imageHeight.roundToPx() }
   //Kaydırma yapıldıktan sonra resim alanının ne kadar görünüp görünmeyeceğini belirlemekte kullanılacak değeri oluşturma
    val offset = scrollState.firstVisibleItemScrollOffset.coerceAtMost(maxOffset)
    val offsetProgress = 0f.coerceAtLeast(offset * 3f - 2f * maxOffset) / maxOffset

    TopAppBar(
        contentPadding = PaddingValues(),
        backgroundColor = Color.White,
        modifier = Modifier
            .height(AppBarExpandedHeight)
            .offset { IntOffset(x = 0, -offset) },
        elevation = if (offset == maxOffset) 4.dp else 0.dp
    ) {
        Column {
            //Resim alanının görünüm özelliklerini belirleme
            Box(modifier = Modifier.height(imageHeight)) {
                if (offset != maxOffset) {
                    Image(
                        painter = painterResource(id = R.drawable.aksaz),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        //Kaydırma sonucunda elde edilen offset değerine göre resmin alanının görünürlüğünü belirleme
                        modifier = Modifier.offset { IntOffset(0, offset / 2) },
                    )
                }
            }//Toolbar alanının görünüm özelliklerini belirleme
            Column(
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(Color(0xFF024D66), Color(0xff032C45))
                        )
                    )
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(AppBarCollapsedHeight),
                verticalArrangement = Arrangement.Center
            ) { //Toolbar alanındaki başlığın görünüm özelliklerini belirleme
                Text(color =Color.White,
                    text = "Akgöl / Sinop",
                    fontSize = 24 .sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .padding(horizontal = (16 + 28 * offsetProgress).dp)
                        .scale(1f - 0.25f * offsetProgress)
                )
            }
        }
    }
}
// "ok (->)" ikonunu Toolbar'a yerleştirme
@Composable
fun ToolbarActions(modifier: Modifier) {
    Row(modifier = modifier.fillMaxWidth().height(56.dp)) {
        IconAction(Modifier.padding(top = 16.dp, start = 16.dp), Icons.Default.ArrowBack)
    }
}
//Toolbar'daki "ok (->)" ikonunu oluturma
@Composable
private fun IconAction(modifier: Modifier, image: ImageVector) {
    IconButton(
        onClick = {},
        modifier = modifier.size(32.dp)
    ) {
        Icon(
            imageVector = image,
            contentDescription = "",
            modifier = Modifier.padding(4.dp)
        )
    }
}
//İçerik yazısını tanımlama ve arayüzde gösterme
@Composable
fun Content(scrollState: LazyListState) {
    LazyColumn(contentPadding = PaddingValues(top = AppBarExpandedHeight), state = scrollState) {
        item {
                Text(color =Color.White,
                    modifier = Modifier.padding(20.dp),
                    text = "Akgöl ve Akgöl Yaylası Hakkında Genel Bilgiler\n\n" +
                            "Akgöl, Kastamonu’nun Hanönü İlçesi ile Sinop’un Ayancık İlçesi arasında Hanönüne yaklaşık 20 km uzaklıkta yemyeşil köknar ağaçları arasında kalmış saklı bir göl. Abant Gölü veya Uzungöl ile kıyaslanamayacak kadar bakir olan bu gölün etrafındaki yeşillikler övgüyü fazlası ile hakediyor. Tertemiz havası ve kuş cıvıltıları sizleri karşılıyor. Göl, ortalama 3 dönümlük bir alanı kaplayan, iki çayın birleştirerek oluşturduğu yapay bir göldür. Günübirlik geziler için uygun olan göl civarındaki orman içlerinde piknik alanları bulunmaktadır. Akgöl 1200 metre rakımlı yaylaya da sahip. Her mevsim ayrı bir güzelliği bulunan bu göl ve çevresinde bilimsel araştırmalar yapmak için gelen botanikçileri görebilirsiniz. Kışın bembeyaz kar örtüsü, ilkbaharda çiçeklerle kaplanan yürüyüş yolu, Sonbaharda ise sizi renk cümbüşü ile sizleri karşılıyor. Yazın ise yaylaya başka bir güzel.\n\n" +
                            "Göl, Bakanlar Kurulu’nun kararı ile 1991 yılında Akgöl Yaylası adı ile bir turizm noktası ilan edilmiş. Ancak yeteri kadar tanıtımının yapılmamış olması ve yolların kullanışsız olmasından ötürü hak ettiği ilgiyi görememektedir.\n" +
                            "\n" +
                            "Çevresindeki ormanlarda yaban domuzu, ayı, kurt, çakal ve tavşan gibi yabani hayvanlara rastlamak mümkün. Bu yüzden dikkatli olmanızda yarar var. Gölün yanında Orman İşletme Müdürlüğüne ait bir tesis bulunduğunu hatırlatmakta da fayda var.\n\n" +
                            "Akgöl’de Neler Yapılır?\n\n" +
                            "Göl etrafında bolca mesire alanı vardır. Piknik ve mangal yapmak için ızgara ve piknik alanları mevcuttur.  Sezon uygunsa göl üzerinde sandal sefasına çıkmanız da mümkün. Kışın ise burası ayrı bir güzeldir. Kartpostallık fotoğraflar çekebilir ve küçük tepelerden kızakla kayabilirsiniz.\n\n" +
                            "Gölün etrafında bulunan yürüyüş parkurunu ilkbaharda çiçekler eşliğinde dolaşırken, sonbaharda rengârenk yaprakların oluşturduğu manzarada dolaşmak bambaşka bir zevk.\n\n" +
                            "Akgöl çadır kampı yapmak için de ideal bir yer. Orman işletmeleri tesisinin yan tarafında çadırınızı kurabilir, ertesi sabah kimsenin olmadığı göle nazır güzel bir güne uyanabilirsiniz.")
        }
    }
}
