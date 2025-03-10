@import com.grochu.libraryadminclient.DAL.Book
@import java.util.List
@param List<Book> books
@param int page
@param int pageNumber

<!doctype html>
<html>
<head>
    <title>Biblioteka - admin</title>
</head>
<body>
<a href="/">Wróć do menu</a>
<h1>Lista książek</h1>

@for(Book book : books)
    <a href="/books/${book.getId()}"><div style="background-color: red;" onclick=""><h2>${book.getTitle()}</h2></div></a>
@endfor

@if(page != pageNumber)
    <a href="?page=${page-1}">&lt</a>
@else
    &lt
@endif
@for(int i = 1; i <= pageNumber; i++)
    @if(page == i)
        ${i}
    @else
        <a href="?page=${i}">${i}</a>
    @endif
@endfor
@if(page != pageNumber)
    <a href="?page=${page+1}">&gt</a>
@else
    &gt
@endif

</body>
</html>