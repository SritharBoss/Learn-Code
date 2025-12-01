import { useEffect, useState } from "react";
import Header from "./components/header";

function App() {
  const headerValue = "Cars"
  var initialItems = ["Mustang", "Lambo", "Porche", "BMW"];
  const [statefulValue, setMyValue] = useState(() => {
    try {
      const savedItems = localStorage.getItem("cars");  
      return savedItems!=null && savedItems.length>3 ? JSON.parse(savedItems) : initialItems;
    } catch (error) {
      return initialItems
    }
  });

  useEffect(()=>{
    if(statefulValue.length>0){
      localStorage.setItem("cars",JSON.stringify(statefulValue))
    }else{
      localStorage.setItem("cars",JSON.stringify(initialItems))
    }
  })

  return (
    <>
      <Header value={headerValue} />
      <ul className="list-group">
        {statefulValue.map((value) => {
          return (
            <li
            className={"list-group-item"}
              key={value}
              id={value}
              onMouseEnter={(e)=>{e.target.classList.add("active")}}
              onMouseLeave={(e)=>{e.target.classList.remove("active")}}
              onClick={(e) => {
                setMyValue(statefulValue.filter((item)=>{return item!==e.target.textContent}))
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
