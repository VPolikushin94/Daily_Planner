# Описание приложения

Проект представляет собой небольшое приложение ежедневника, со следующим функционалом:

- Отображение списка задач

  <img src="https://github.com/VPolikushin94/DailyPlanner/assets/121296133/88b416b2-b93f-435a-8e53-81cfcbf66c5e" width="250"/>

- Создание новой задачи

  <img src="https://github.com/VPolikushin94/DailyPlanner/assets/121296133/90e7795d-92e0-405b-afe4-550955a9ef96" width="250"/>

- Просмотр созданной задачи

  <img src="https://github.com/VPolikushin94/DailyPlanner/assets/121296133/1f532e3f-b1d0-4a29-b55c-8b12816466f8" width="250"/>

  Ниже представлен список требований и особенностей различных экранов приложения.

## Общие требования

&emsp; ✅ Структурированный чистый код.

&emsp; ✅ Использование сервисного слоя для подготовки данных. Сервис лежит по пути /core/data/network/impl/NoteServiceImpl.kt. Моковые данные, имитирующие данные из сети /core/data/mock_data/MockNotes.kt

&emsp; ✅ Адаптивная верстка с использованием Constraint Layout.

&emsp; ✅ Использование архитектурных паттернов.

&emsp; ✅ Поддержка устройств, начиная с Android 8 (minSdkVersion = 26).

&emsp; ✅ Ориентация - портретная.

&emsp; ✅ Добавить экран создания дела.

&emsp; ✅ Создание custom view компонента. Создал компонент для отображения часа в таблице <img src="https://github.com/VPolikushin94/DailyPlanner/assets/121296133/b79bca2d-5e30-4ef7-9f2a-863b1fef7dab" width="30"/>. Путь /notes/ui/custom_view/DayHourView.kt

&emsp; ✅ Для локального хранения используется Room.

&emsp; ✅ Покрытие Unit-тестами.

&emsp; ✅ Поддерживаются светлая и темная темы.

## Главный экран -- экран списка задач

На этом экране пользователь может выбрать день и в нижней части экрана отобразится таблица с задачами. В каждой ячейке таблицы указан один час из дня и если в это время есть задача, она отобразится блоком (название и время).

### Особенности экрана

- В календаре огонечком помечаются дни в которых есть дела.
- Выбранный день отмечается синим кругом.
- Список задач можно прокручивать во вертикали и по горизонтали, если есть несколько дел в один час и они не помещаются на экране.
- При нажатии на "+" в верхнем правом углу, пользователь переходит на экран создания новой задачи.
- При нажатии на задачу, пользователь переходит на экран с подробным описанием.

## Новая задача -- экран создания новой задачи

Заполнив поле "Название" и выбрав время начала и окончания дела, пользователь может добавить новую задачу в свой ежедненик.

### Особенности экрана

- Поле "Название" является обязательным.
- Время начала и окончания задачи не может быть одинаковой.
- Время окончания задачи не может быть раньше времени начала.
- При неправильно выбранных данных показывается сообщение с подсказкой.
- При переходе на экран, дата начала и окончания работы устанавливается дата, выбранная в календаре главного экрана.

## Подробнее -- экран с детальным описанием задачи

На данном экране пользователь может посмотреть: название задачи, ее время начала и окончания, описание.
