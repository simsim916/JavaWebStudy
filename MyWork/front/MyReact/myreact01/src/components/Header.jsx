// ** 1컴포넌트 1화일
// => 그러므로  export default 많이 사용됨.



export default function Header(props) {
    return (
        <header>
            <h1>🍅header🍅</h1>
            <h3>안녕하세요</h3>
            <p>금주의 Best_Dress : color={props.bestDress.color} , style={props.bestDress.style}</p>
        </header>


    );
};