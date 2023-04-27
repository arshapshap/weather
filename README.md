# Приложение погоды

Android-приложение для отображения текущей погоды и прогноза погоды на 5 дней в заданном городе, использующее публичное API сервиса [OpenWeatherMap](https://openweathermap.org). 
Приложение построено согласно Clean Architecture и реализует паттерн MVVM в UI-слое.

Данный проект был выполнен в качестве домашнего задания в рамках обучения разработке Android-приложений в КФУ ИТИС.

## Функционал

* Получение погоды по введенному названию города или текущему местоположению;
* Отображение текущей погоды и прогноза на ближайшие 5 дней каждые 3 часа;
* Кеширование данных в БД приложения;

<image src='https://user-images.githubusercontent.com/48681339/234848598-8ae0f4ad-a4c1-4156-aff0-a32556d6cd26.gif' width=200 />

## Используемые технологии

* Kotlin
* Android SDK
* Clean Architecture
* MVVM, LiveData
* Coroutines
* Dagger 2
* Room
* Retrofit
* Glide
* RecyclerView


## Подробнее
<details><summary>Подробное описание архитектуры</summary>
  
 
### UI-слой
<p float="left">
  <img src="https://user-images.githubusercontent.com/48681339/234835392-ecb0de7f-1b1c-4575-b522-280359521221.jpg" width="200" />
  <img src="https://user-images.githubusercontent.com/48681339/234835067-f791b84f-f875-4252-a22e-26ffe8c34833.jpg" width="200" />
  <img src="https://user-images.githubusercontent.com/48681339/234835826-051be4f7-839d-4288-84bb-ff2df3f7906c.jpg" width="200" />
  <img src="https://user-images.githubusercontent.com/48681339/234839328-4051d7f0-1cb5-4115-b4cb-b08c55e9cf45.jpg" width="200" />
</p>

- **Главный экран:**
  * Поле для ввода названия города;
  * Кнопка для отображения погоды по введенному названию города;
  * Кнопка для отображения погоды по местоположению;
  * (После получения ответа от сервера) Блок с названием города и текущей температурой;

  <sub>При нажатии на первую кнопку отправляется запрос на получение информации по указанному в поле названию города. Если город не был найден или сеть недоступна, отобразится соответствующее Toast-уведомление об ошибке, иначе информация из ответа отобразится на экране.

  <sub>При нажатии на вторую кнопку запрашивается разрешение на получение местоположения. Если пользователь отказывается, появляется диалоговое окно с информацией о том, зачем необходимо разрешение. Если пользователь запретил показывать запрос разрешения или несколько раз отклонил его, диалоговое окно позволяет перейти в настройки телефона для ручной выдачи разрешения.
    
  <sub>После получения разрешения при нажатии на вторую кнопку отправляется запрос на получение информации по текущим координатам. Если местоположение недоступно (на устройстве отключен GPS) или сеть недоступна, отобразится соответствующее Toast-уведомление об ошибке, иначе информация из ответа отобразится на экране.

  <sub>При нажатии на блок с названием города открывается `BottomSheetDialogFragment` с подробной информацией о текущей погоде. В bundle передаются данные о текущей погоде, в том числе координаты выбранного города. Запрос на получение прогноза погоды происходит при открытии `BottomSheetDialogFragment` с использованием полученных в bundle координат.

- **Экран BottomSheetDialogFragment:**
  * Иконка погоды;
  * Список параметров текущей погоды: город, температура, влажность, давление, скорость ветра;
  * Список с прогнозом погоды на 5 дней. Каждый элемент содержит иконку дату, время, иконку погоды и температуру.

  <sub>Оба списка отображаются с помощью `RecyclerView`. Иконки загружаются по URL с помощью `Glide`.
    
  <sub>У каждого из фрагментов есть соответствующая `ViewModel`, создающая с помощью аннотаций `@AssistedInject` и `@AssistedFactory`. В учебных целях координаты выбранного города с фрагмента `BottomSheetDialogFragment` передаются в `ViewModel` через конструктор с помощью аннотации `@Assisted`. Для передачи информации из `ViewModel` во фрагменты используется `LiveData`.
    
  <sub>Также доступен английский язык. При повороте экрана приложение работает исправно.

### Domain-слой

- Для каждого из возможных действий пользователя создан соответствующий `UseCase`:
  * GetLocationUseCase;
  * GetWeatherByCityNameUseCase;
  * GetWeatherByLocationUseCase;
  * GetWeatherForecastByLocationUseCase;
    
- Созданы интерфейсы репозиториев для получения текущей погоды, прогноза погоды и местоположения:
  * LocationRepository;
  * WeatherRepository;
  * WeatherForecastRepository;
    
### Data-слой

- Реализован каждый интерфейс репозитория из Domain-слоя.
  * LocationRepositoryImpl: 
    
    <sub>Для получения текущего местоположения обращается к классу `LocationDataSource`, который в свою очередь использует `FusedLocationProviderClient`.

  * WeatherRepositoryImpl:
      
    <sub>Для получения текущей погоды обращается к `WeatherApiService`, генерируемому с помощью `Retrofit`, а также к `WeatherDao`, генерируемому с помощью `Room`. Если в БД есть запись с погодой в выбранном городе, обновленная менее чем минуту назад, возвращается информация из БД, иначе отправляется запрос на сервер. Ответ с сервера кэшируется в БД.
  * WeatherForecastRepositoryImpl:
      
    <sub>Работает аналогично `WeatherRepositoryImpl`. В учебных целях (для знакомства с аннотацией `@Named`) для сервисов `WeatherApiService` и `WeatherForecastApiService` используются разные экземпляры `Retrofit`.
      
</details>

## Установка

1. Склонируйте репозиторий: 

```bash
git clone https://github.com/arshapshap/weather
```

2. Создайте файл `credentials.properties` в корне проекта.
2. Создайте API ключ на сайте [OpenWeatherMap](https://openweathermap.org) и добавьте его в файл `credentials.properties` в формате: 

```gradle
WEATHER_API_KEY=your_key_here
```
