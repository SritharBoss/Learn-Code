const users=["Default User"]
function handler(req,res){
    const url=req.url
    const method=req.method
    if(url=='/'){
        res.setHeader('content-type','text/html')
        res.write(
            `<html>
            <title>Welcome</title>
            <body>
            <h1>Welcome to my Node Application!</h1>
            <a href="/create-user">Click Here to Add User</a>
            </body>
            </html>`)
        res.end();
    }else if(url==='/create-user'){
        res.setHeader('content-type','text/html')
        res.write(
            `<html>
            <title>Welcome</title>
            <body>
            <h1>Welcome to my Node Application!</h1>
            <form action="/users" method="POST">
            <input type="text" name="username"><button>Submit</button>
            </form>
            </body>
            </html>`)
        res.end()
    }else if(url==='/users' && method==='POST'){
        res.setHeader('content-type','text/html')
        const temp=[]
        req.on('data',(chunk)=>{
            temp.push(chunk)
        })

        req.on('end',()=>{
            res.statusCode=302
            res.setHeader('location','/users')
            users.push(Buffer.concat(temp).toString().split('=')[1])
            res.end()
        })

    }else if(url==='/users'){
        res.setHeader('content-type','text/html')
        res.write(
            `<html>
            <title>User List</title>
            <body>
            <a href="/create-user">Add Another User</a>
            ${(function (){
                var str="";
                for(i of users)
                {
                    str=str+"<li>"+i+"</li>"
                }
                return "<ul>"+str+"</ul>"
            })()}
            </body>
            </html>`)
        res.end();
    }
}

module.exports = {handler}