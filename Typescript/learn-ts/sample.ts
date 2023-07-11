const data = `{
	"data": [
		{
			"date": "25-10-1996",
			"time": "05:00",
			"accounts": [
				{
					"name": "Cash",
					"type": "cash",
					"amount": "500",
					"logo": "logoPath",
					"yestAmount": "450"
				},
				{
					"name": "Pvl SBI",
					"type": "bank",
					"amount": "500",
					"logo": "logoPath",
					"yestAmount": "750"
				}
			],
			"services": [
				{
					"name": "SunDirect",
					"type": "ec",
					"amount": "500",
					"logo": "logoPath",
					"yestAmount": "700"
				},
				{
					"name": "Bsnl",
					"type": "ec",
					"amount": "500",
					"logo": "logoPath",
					"yestAmount": "400"
				}
			],
			"custBalance": [
				{
					"custName1": "name1",
					"type": "d",
					"Amount": "50",
					"yestAmount": "50"
				},
				{
					"custName2": "name2",
					"type": "c",
					"Amount": "100"
				}
			],
			"cashBal": {
				"500": 1,
				"100": 3
			},
			"todayExpenses": [
				{
					"name": "Modem EC",
					"Notes": "For SHop Mobile",
					"type": "d",
					"amount": "666"
				}
			]
		}
	]
}`;



JSON.parse(data).data.forEach(element => {
  let a=element.custBalance.filter(element=>{
    return element.custName1=='name1';
  });
  console.log(a)
});



