@import com.grochu.libraryadminclient.Domain.Author
@import java.util.List
@param List<Author> authors
@param int page
@param int pageNumber
@param String search = null

@template.layout.main(content = @`
<div>
    <a href="/" style="display: block; float: left;">Wróć do menu</a>
    <form href="/" style="display: block; float: right;">
        <input name="search" id="search"/>
        <button type="submit">Wyszukaj</button>
    </form>
</div>
<br/>
<h1>Lista książek</h1>
<a href="/authors/add">Dodaj nowego autora</a>

@for(Author author : authors)
    <a href="/authors/${author.getId()}"><div style="background-color: green;" onclick=""><h2>${author.getName()}</h2></div></a>
@endfor

@if(page != 1)
    <a href="?page=${page-1}">&lt</a>
@else
    &lt
@endif
@for(int i = 1; i <= pageNumber; i++)
    @if(page == i)
        ${i}
    @elseif(search == null)
        <a href="?page=${i}">${i}</a>
    @else
        <a href="?page=${i}&search=${search}">${i}</a>
    @endif
@endfor
@if(page != pageNumber)
    <a href="?page=${page+1}">&gt</a>
@else
    &gt
@endif
`)