import { useContext } from "react"
import MyContext from "../context/MyContext"
import PropTypes from 'prop-types'

export function Header(props) {
    const context=useContext(MyContext)
    return (
        <>
            <section className="section" onClick={()=>{console.log("Header Clicked");context.setName("Count Is "+props.name)}} >
                <div className="container has-text-centered">
                    <h1 className="title">
                        {context.name}
                    </h1>
                    <p className="subtitle">
                        Click on Above Section <strong className="has-text-primary"> To Update Count </strong>!
                    </p>
                </div>
            </section>
        </>
    )
}

Header.propTypes = {
    name: PropTypes.number
};

