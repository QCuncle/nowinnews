package me.qcuncle.nowinnews.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import me.qcuncle.nowinnews.domain.model.ArticleXpath

@ProvidedTypeConverter
class SiteConfigTypeConvertor {

    @TypeConverter
    fun sourceToString(articleXpath: ArticleXpath): String {
        return "${articleXpath.position},${articleXpath.title},${articleXpath.url},${articleXpath.popularity},${articleXpath.imageUrl},${articleXpath.parameter}"
    }

    @TypeConverter
    fun stringToSource(article: String): ArticleXpath {
        return article.split(',').let { articleXpath ->
            ArticleXpath(
                position = articleXpath[0],
                title = articleXpath[1],
                url = articleXpath[2],
                popularity = articleXpath[3],
                imageUrl = articleXpath[4],
                parameter = articleXpath[5]
            )
        }
    }
}