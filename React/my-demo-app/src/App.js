import { useState } from "react";
import Header from "./components/header";

function App() {
  const headerValue="Cars"
  const items = ["Mustang", "Lambo", "Porche", "BMW"];

  const [myValue,setMyValue] = useState(items[0]);
  

  return (
    <>
      <Header value={headerValue}/>
      <ul className="list-group">
        {items.map((value) => {
          return (
            <li
              key={value}
              id={value}
              className={ myValue===value ? "list-group-item active" : "list-group-item list-group-item-action" }
              onClick={() => {
                setMyValue(value)
              }}
            >
              {value}
            </li>
          );
        })}
      </ul>
    </>
  );
}

export default App;
