@import com.grochu.libraryadminclient.Domain.User
@import java.util.List
@param List<User> users
@param int page
@param int pageNumber
@param String search = null
@param Boolean borrowMode = false
@param Long copyId = null
@param Long bookId = null

@template.layout.main(content = @`
<div>
    <a href="/" style="display: block; float: left;">Wróć do menu</a>
    <form href="/" style="display: block; float: right;">
        <input name="search" id="search"/>
        @if(borrowMode != null && borrowMode)
            <input type="hidden" value="${borrowMode}" name="borrowMode"/>
            <input type="hidden" value="${copyId}" name="copyId"/>
            <input type="hidden" value="${bookId}" name="bookId"/>
        @endif
        <button type="submit">Wyszukaj</button>
    </form>
</div>
<br/>
<h1>Lista czytelników</h1>

@for(User user : users)
    @if(borrowMode != null && borrowMode)
        <a href="/books/${bookId}/copy/${copyId}/borrow/${user.getId()}"><div style="background-color: yellow;" onclick="">
                <h2>${user.getSurname()} ${user.getName()}</h2></div></a>
    @else
        <a href="/customers/${user.getId()}"><div style="background-color: yellow;" onclick="">
                <h2>${user.getSurname()} ${user.getName()}</h2></div></a>
    @endif
@endfor

@if(page != 1)
    <form href="/" style="display: block; float: left;">
        <input type="hidden" value="${search}" name="search"/>
        @if(borrowMode != null && borrowMode)
            <input type="hidden" value="${borrowMode}" name="borrowMode"/>
            <input type="hidden" value="${copyId}" name="copyId"/>
            <input type="hidden" value="${bookId}" name="bookId"/>
        @endif
        <input type="hidden" value="${page-1}" name="page"/>
        <button type="submit">&lt</button>
    </form>
@else
    &lt
@endif
@for(int i = 1; i <= pageNumber; i++)
    @if(page == i)
        ${i}
    @else
        <form href="/" style="display: block; float: left;">
            <input type="hidden" value="${search}" name="search"/>
            @if(borrowMode != null && borrowMode)
                <input type="hidden" value="${borrowMode}" name="borrowMode"/>
                <input type="hidden" value="${copyId}" name="copyId"/>
                <input type="hidden" value="${bookId}" name="bookId"/>
            @endif
            <button type="submit">${i}</button>
        </form>
    @endif
@endfor
@if(page != pageNumber)
    <form href="/" style="display: block; float: left;">
        <input type="hidden" value="${search}" name="search"/>
        @if(borrowMode != null && borrowMode)
            <input type="hidden" value="${borrowMode}" name="borrowMode"/>
            <input type="hidden" value="${copyId}" name="copyId"/>
            <input type="hidden" value="${bookId}" name="bookId"/>
        @endif
        <input type="hidden" value="${page+1}" name="page"/>
        <button type="submit">&gt</button>
    </form>
@else
    &gt
@endif
`)