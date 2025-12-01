import { useRef, useState } from 'react'
import { Header } from './components/header'
import "./App.css"
import MyContext from './context/MyContext'

export function App() {
  const [count, setCount] = useState(0)
  const [moreThan5, setMoreThan5] = useState(false)
  const [name, setName]=useState('Default Name')
  const btnRef=useRef()

  let buttonClass = "button"
  if (moreThan5) { buttonClass += " is-danger" }

  const btnClick = ()=>{alert(btnRef.current.textContent)}

  return (
    <>
      <MyContext.Provider value={{ name, setName }}>
        <button ref={btnRef} style={{ margin: "10px" }} className={buttonClass} onContextMenu={(eve) => {
          setCount(count - 1);
          if ((count - 1) <= 5) {
            setMoreThan5(false);
          }
          eve.preventDefault();
          return false;
        }} onClick={() => {
          setCount(count + 1)
          if ((count + 1) >= 5) {
            setMoreThan5(true)
          }
        }
        }>
          Count is {count}
        </button>
        <hr style={{height: "10px"}} onClick={btnClick}/>
        <Header name={count} />
      </MyContext.Provider>
    </>
  )
}
