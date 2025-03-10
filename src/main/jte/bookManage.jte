@import com.grochu.libraryadminclient.DAL.Copy
@import com.grochu.libraryadminclient.DAL.Book
@import java.util.List
@param Book book
@param List<Copy> copies

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Książka: ${book.getTitle()}</title>
</head>
<body>
<a href="/books">Wróć do listy</a>
<h1>Tytuł: ${book.getTitle()}</h1>
<h2>Autor: ${book.getAuthor().getName()}</h2>
<h2>Rok wydania: ${book.getPublishYear()}</h2>

@for(Copy copy : copies)
    ${copy.getId()}
@endfor

</body>
</html>