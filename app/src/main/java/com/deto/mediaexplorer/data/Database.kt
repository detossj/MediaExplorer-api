package com.deto.mediaexplorer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.deto.mediaexplorer.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Category::class, Element::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun categoryDao(): CategoryDao
    abstract fun elementDao(): ElementDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "App_database")
                   /* .addCallback(DatabaseCallback(context)) // <- Aquí se insertan los datos iniciales*/
                    .build()
                    .also { Instance = it }
            }
        }
    }
    /*private class DatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // Insertar datos solo cuando se crea la BD por primera vez
            CoroutineScope(Dispatchers.IO).launch {
                val categoryDao = getDatabase(context).categoryDao()
                val elementDao = getDatabase(context).elementDao()

                // Insertar categorías
                val defaultCategories = listOf(
                    Category(1, "Anime", R.drawable.manga_24px),
                    Category(2, "Peliculas", R.drawable.movie_24px),
                    Category(3, "Series", R.drawable.live_tv_24px),
                    Category(4, "Novela", R.drawable.menu_book_24px),
                    Category(5, "Other", null)
                )
                defaultCategories.forEach { categoryDao.insert(it) }

                // Insertar elementos
                val defaultElements = listOf(


                    Element(title = "Naruto", description = "Naruto, un aprendiz de ninja de la Aldea Oculta de Konoha es un chico travieso que desea llegar a ser el Hokage de la aldea para demostrar a todos lo que vale. Lo que descubre al inicio de la historia es que la gente le mira con desconfianza porque en su interior está encerrado el demonio Kyubi que una vez destruyó la aldea, y que el anterior líder de la misma tuvo que encerrar en su cuerpo siendo aún muy pequeño, a coste de su vida. Aunque sus compañeros no saben esto, tampoco le aprecian porque es mal estudiante y siempre está haciendo bromas. Sin embargo, la forma de actuar y la determinación de Naruto demuestran a los demás que puede llegar muy lejos, y el recelo de los otros chicos se va disipando. Naruto y sus compañeros Sakura y Sasuke, junto a su maestro Kakashi tendrán que enfrentarse a una serie de combates y misiones a lo largo de la historia que les permitirán mejorar y crecer. Naruto se vera enfrentado a sus principales enemigos Akatsuki, Itachi y Kisame.", classification = 4, imagen = R.drawable.naruto, categoryId = 1),
                    Element(title = "Dragon Ball Z", description = "En Dragon ball Z Goku se ha convertido en un adulto y está casado con Milk, con la que tiene un hijo llamado Gohan. En esta segunda saga de Dragon ball Goku descubrirá que no es un terricola, sino que pertenece a una raza de guerreros conocida por ser una de las más poderosas de la galaxia, para posteriormente dar paso a los verdaderos enemigos de la serie. Para poder vencer a los nuevos enemigos que irán surgiendo, Goku y sus amigos tendrán que viajar por otros planetas e incluso al cielo y al infierno. Cada enemigo será más fuerte que el anterior, por lo que tendrán que entrenar muy duro para poder derrotarlos además de que se les irán uniendo nuevos personajes que les ayudarán a vencerlos.", classification = 4, imagen =  R.drawable.dragonballz, categoryId = 1),
                    Element(title ="One Piece", description = "Una historia épica de piratas, donde narra la historia de \"Monkey D. Luffy\" quien cuando tenia 7 años, comió accidentalmente una \"Akuma no mi\"(Fruta del diablo) la cual le convirtió en un hombre de goma. Por otra parte \"Gol D. Roger\" conocido como \"El rey de los Piratas\" quien fuera ejecutado por la Marine, habló antes de morir, acerca de su mayor tesoro,el \"One Piece\" escondido en la \"Gran line\". Esta noticia desató la gran era de los piratas lanzando a incontables piratas a ese lugar, en busca del \"One Piece\" el tesoro perdido. Diez años después, Luffy inspirado en \"Gol D. Roger\" y un pirata de nombre Akagami no Shanks (Shanks el pelirrojo) se convierte en pirata deseando ser el próximo \"Rey de los Piratas\" y zarpar para conocer amigos y tener aventuras con ellos, teniendo como meta encontrar el \"One Piece\".", classification = 5, imagen = R.drawable.onepiece, categoryId = 1),
                    Element(title ="Minecraft", description = "¡Bienvenidos al mundo de Minecraft, donde la creatividad no solo te ayuda a crear, es esencial para la supervivencia! Cuatro inadaptados—Garrett “El Hombre de la Basura” Garrison (Momoa), Henry (Hansen), Natalie (Myers) y Dawn (Brooks)—se encuentran luchando con problemas ordinarios cuando de repente son arrastrados a través de un misterioso portal al Overworld: una extraña tierra cuadrada que prospera con la imaginación. Para regresar a casa, tendrán que dominar este mundo (y protegerlo de cosas malignas como los Piglins y los Zombis) mientras emprenden una búsqueda mágica con un inesperado experto creador, Steve (Black). Juntos, su aventura desafiará a los cinco a ser audaces y reconectarse con las cualidades que hacen a cada uno de ellos creativamente únicos... las mismas habilidades que necesitan para prosperar de nuevo en el mundo real.", classification = 4, imagen = R.drawable.minecraft, categoryId = 2),
                    Element(title ="Blancanieves", description = "En esta adaptación, Blancanieves es una princesa que, tras la muerte de su madre y el ascenso al trono de su vanidosa madrastra, debe huir al bosque para escapar de los celos de la Reina. Allí encuentra refugio con siete criaturas mágicas y, con la ayuda de Jonathan (interpretado por Andrew Burnap), un joven aldeano que defiende la justicia, se enfrenta a la tiranía de la Reina para devolver la alegría a su reino. .", classification = 2, imagen = R.drawable.blancanieves, categoryId = 2),
                    Element(title ="Capitan America: Un nuevo mundo", description = "Después de reunirse con el recién electo presidente de los Estados Unidos, Thaddeus Ross (interpretado por Harrison Ford), Sam Wilson se ve envuelto en un incidente internacional. Debe descubrir la razón de un nefasto complot mundial antes de que el verdadero artífice detrás del mismo haga que el mundo entero entre en caos. ", classification = 4, imagen = R.drawable.capitanamerica, categoryId = 2),
                    Element(title ="Daredevil", description = "Matt Murdock perdió la vista en un accidente durante su infancia, pero el incidente agudizó sus otros sentidos a niveles sobrehumanos. De día, ejerce como abogado comprometido con la justicia; de noche, se convierte en Daredevil, enfrentándose a la corrupción y el crimen organizado que amenazan su barrio. A lo largo de la serie, Matt se enfrenta a enemigos como Wilson Fisk (Kingpin), un poderoso criminal que busca controlar la ciudad desde las sombras. La narrativa explora los dilemas morales de Murdock, su fe católica y las consecuencias de su doble vida. ", classification = 5, imagen = R.drawable.daredevil, categoryId = 3),
                    Element(title ="The Punisher", description = "Después de vengar la muerte de su esposa e hijos, Frank Castle descubre una conspiración que va más allá del crimen organizado de Nueva York. Ahora conocido como \"The Punisher\", debe desentrañar la verdad sobre las injusticias que afectan no solo a su familia, sino a toda la sociedad. ", classification = 5, imagen = R.drawable.thepunisher, categoryId = 3),
                    Element(title ="Iron Fist", description = "Iron Fist es una serie de televisión de Marvel que se estrenó en 2017 en Netflix, protagonizada por Finn Jones como Danny Rand, un experto en artes marciales con la capacidad de invocar el poder místico del \"Puño de Hierro\". La serie sigue a Rand mientras regresa a Nueva York después de haber sido dado por muerto durante 15 años, intentando reconectar con su pasado y el legado de su familia, enfrentándose a elementos criminales que corrompen la ciudad. ", classification = 4, imagen = R.drawable.ironfist, categoryId = 3),
                    Element(title ="Papá a la deriva", description = "La historia sigue al Capitán Bruno Montt (interpretado por Gonzalo Valenzuela), un estricto oficial de la Armada de Chile y viudo desde hace cinco años, que intenta criar a sus cuatro hijos con disciplina militar en Valparaíso. Sin embargo, sus hijos —Cristóbal, Esmeralda, Arturo y Marina— constantemente se meten en problemas, desafiando la rígida estructura que Bruno intenta imponer en el hogar.", classification = 2, imagen = R.drawable.papaaladeriva, categoryId = 4),
                    Element(title ="Pobre gallo", description = "Nicolás Pérez de Castro (Álvaro Rudolphy) es un exitoso empresario santiaguino, adicto al trabajo y a las redes sociales, que ha descuidado su vida familiar. Su mundo se desmorona cuando su esposa, Florencia (Antonia Zegers), lo abandona por otro hombre. Tras un colapso nervioso, es diagnosticado con vértigo agudo debido al estrés y se ve obligado a desconectarse de su agitada vida. Decide entonces trasladarse con sus hijos, Camila (Montserrat Ballarín) y Borja (Augusto Schuster), al fundo familiar en Yerbas Buenas, su pueblo natal, donde reside su padre Onofre (Jaime Vadell).", classification = 5, imagen = R.drawable.pobregallo, categoryId = 4),
                    Element(title ="Pituca sin lucas", description = "La historia gira en torno a María Teresa \"Techi\" de la Puente (Emilia Drago), una mujer de la alta sociedad limeña que lleva una vida lujosa y sin preocupaciones. Su mundo se desmorona cuando su esposo enfrenta problemas financieros y huye del país, dejándola sin recursos económicos. Obligada a reinventarse, Techi se muda con sus tres hijas a un barrio de clase media, donde debe adaptarse a una nueva realidad alejada de los lujos a los que estaba acostumbrada.", classification = 4, imagen = R.drawable.pitucasinlucas, categoryId = 4)

                )
                defaultElements.forEach { elementDao.insert(it) }
            }
        }
    }
*/
}