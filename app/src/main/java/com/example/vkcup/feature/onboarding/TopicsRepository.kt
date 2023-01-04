package com.example.vkcup.feature.onboarding

import java.util.LinkedList
import java.util.Queue
import kotlin.random.Random
import kotlin.random.nextInt

class TopicsRepository {
    private val topicPool: Queue<Topic> = LinkedList(
        listOf(
            Topic("Юмор", false),
            Topic("Еда", false),
            Topic("Кино", false),
            Topic("Рестораны", false),
            Topic("Сириус", false),
            Topic("Прогулки", false),
            Topic("Политика", false),
            Topic("Новости", false),
            Topic("Автомобили", false),
            Topic("Сериалы", false),
            Topic("Рецепты", false),
            Topic("VK Образование", false),
            Topic("Работа", false),
            Topic("Хохотач", false),
            Topic("Отдых", false),
            Topic("Спорт", false),
            Topic("Животные", false),
            Topic("Программирование", false),
            Topic("Кошки", false),
            Topic("Футбол", false),
            Topic("Музыка", false),
            Topic("Езда верхом", false),
            Topic("Собаки", false),
            Topic("Видеоигры", false),
            Topic("Черви", false),
            Topic("Химия", false),
            Topic("Адам Рагусеа", false),
            Topic("Литература", false),
            Topic("Искуственный интеллект", false),
            Topic("Иммиграция", false),
            Topic("Компьютерное железо", false),
            Topic("Инновации", false),
            Topic("Строительство", false),
            Topic("Джин Доусон", false),
            Topic("Пупы", false),
            Topic("Наука", false),
        ).shuffled()
    )

    fun fetchTopics(): List<Topic> = buildList {
        repeat(12) {
            val nextTopic = topicPool.remove()
            add(nextTopic)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun getSimilarTopics(topic: Topic): List<Topic> = buildList {
        repeat(Random.Default.nextInt(2..5)) {
            val nextTopic = topicPool.poll() ?: return@repeat
            add(nextTopic)
        }
    }
}
