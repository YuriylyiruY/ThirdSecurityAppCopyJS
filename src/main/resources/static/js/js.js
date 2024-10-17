const popupCloseNewUser = document.querySelector('.popupCloseNewUser');
const popupCloseAdminPanel = document.querySelector('.popupCloseAdminPanel');

const formSubmit = document.querySelector('.newUserSubmitJS');
const getAllusers = document.querySelector('.getAllUsersJS');
const getAllusers2 = document.querySelector('.getAllUsersJS');
const getAllusers3 = document.querySelector('.getAllUsersJS');

const modCloseAdmin = document.querySelector('.popupCl');
const modCloseNewUser = document.querySelector('.popupOp');


const btnExp = document.querySelector('.btnExp');


/////////////////////////////new user/////////////////////////////////////////////////////////////////////////////////////

const closeHandlerAdmin = () => {
    popupCloseAdminPanel.classList.add('hidden');
    popupCloseAdminPanel.classList.remove('show');
    popupCloseNewUser.classList.add('show');
    popupCloseNewUser.classList.remove('hidden');


}
const closeHandlerNewUser = () => {
    popupCloseAdminPanel.classList.add('show');
    popupCloseAdminPanel.classList.remove('hidden');
    popupCloseNewUser.classList.add('hidden');
    popupCloseNewUser.classList.remove('show');


}
document.addEventListener('click', function (e) {

    if (!e.target.classList.contains('popupOp')) {


        return
    }
    console.log('this goal USERTABLE');
    const popupCloseNewUser = document.querySelector('.popupCloseNewUser');
    const popupCloseAdminPanel = document.querySelector('.popupCloseAdminPanel');
    closeHandlerNewUser();
})

document.addEventListener('click', function (e) {

    if (!e.target.classList.contains('popupCl')) {


        return
    }
    const popupCloseNewUser = document.querySelector('.popupCloseNewUser');
    const popupCloseAdminPanel = document.querySelector('.popupCloseAdminPanel');
    console.log('this goal NEWUSER');
    closeHandlerAdmin();

})

// modCloseAdmin.addEventListener('click', closeHandlerAdmin);
// modCloseNewUser.addEventListener('click', closeHandlerNewUser);


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////sendRequestToServerWithNewUser//////////////////////////////////


formSubmit.addEventListener('submit', (evt) => {
    evt.preventDefault();
    allUsersMethod(evt);
});

function allUsersMethod(evt) {
    const formData = new FormData(evt.target);
    fetch(
        'http://localhost:8080/api/users', {
            method: 'POST',
            body: formData
        },
    )

        .then((response) => {
            if (response.ok) {


                formSubmit.reset();
                getPass();


            } else {

                formSubmit.reset();
                getWarning();
            }
        })
        .catch(() => {
            getWarning();

        });
}


///////////////////////////////////////////////////////
///////////////////////////////////////////////////////
const getPass = () => {
    console.log("ok");
}

const getWarning = () => {
    console.log("not ok");
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////// getAllUsers for adminPage /////////////////////////////////////////////////

getAllusers.addEventListener('click', (evt) => {
    evt.preventDefault();
    getUsers();
})

function getUsers() {
    fetch('http://localhost:8080/api/example', {
        method: 'get',
        headers: {"Content-Type": "Content-Type: text/html"}
    })
        .then(function (response) {
            return response.text()
        })
        .then(function (data) {


            //  console.log('data', data);
            return log(data.toString())

        })
}


function log(data) {
    const logElem = document.querySelector(".log");


    logElem.innerHTML = ` ${data} `;


    let quantity = document.querySelectorAll(".btnExp").length;
    console.log(quantity);


    ////////////////////////////////////////////////////////////////////////////////////

    document.addEventListener('click', function (e) {
        if (!e.target.classList.contains('btnExp')) {


            return
        }
        console.log('get id user after click', e.target.id);

    });


}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//////////////////////////// for popup delete  get from server ////////////////////////////

getAllusers2.addEventListener('click', (evt) => {
    evt.preventDefault();
    deleteMethodGet();
})

function deleteMethodGet() {
    fetch('http://localhost:8080/api/deleteMod', {
        method: 'get',
        headers: {"Content-Type": "Content-Type: text/html"}
    })
        .then(function (response) {
            return response.text()
        })
        .then(function (data) {


            //  console.log('data', data);
            return logMod(data.toString())

        })
}


function logMod(data) {
    const logElem = document.querySelector(".logMod");


    logElem.innerHTML = ` ${data} `;


    let quantityDel = document.querySelectorAll(".deleteS").length;
    console.log('quantity deleteMod', quantityDel);


    document.addEventListener('click', function (ev) {
            if (!ev.target.classList.contains('btnForDelForm')) {
                return
            }
            const deleteUser = document.querySelector(`.formDel${ev.target.id}`);
            console.log("fffffffffff", deleteUser);


            deleteUser.addEventListener('submit', function (e) {
                e.preventDefault();
                console.log('this is delete button ', e.target.id);
                const formDataDel = new FormData(e.target);
                console.log(e.target);
                fetch(
                    `http://localhost:8080/api/users`, {
                        method: 'DELETE',
                        body: formDataDel
                    },
                )

                    .then((response) => {
                        if (response.ok) {


                            callFetch();


                            getPass();


                        } else {


                            getWarning();
                        }
                    })
                    .catch(() => {
                        getWarning();

                    });


            });


        }
    )


    document.addEventListener('click', function (e) {

        if (!e.target.classList.contains('btnDelete')) {


            return
        }
        console.log('this goal show', e.target.id);
        const modOpenDeleteS = document.querySelector(`.deleteS${e.target.id}`);
        modOpenDeleteS.classList.remove('show');
        modOpenDeleteS.classList.add('hidden');


    });

    function callFetch() {

        fetch('http://localhost:8080/api/example', {
            method: 'get',
            headers: {"Content-Type": "Content-Type: text/html"}
        })
            .then(function (response) {
                return response.text()
            })
            .then(function (data) {


                //  console.log('data', data);
                return log(data.toString())

            })
    }

    function log(data) {
        const logElem = document.querySelector(".log");


        logElem.innerHTML = ` ${data} `;


        let quantity = document.querySelectorAll(".btnExp").length;
        console.log(quantity);

    }


}


//////////////////////////// eventListeners for buttons del//////////////////

document.addEventListener('click', function (e) {

    if (!e.target.classList.contains('btnExp')) {


        return
    }
    console.log('this goal show', e.target.id);


    const modOpenDeleteS = document.querySelector(`.deleteS${e.target.id}`);
    modOpenDeleteS.classList.remove('hidden');
    modOpenDeleteS.classList.add('show');

});
document.addEventListener('click', function (e) {

    if (!e.target.classList.contains('btnDel')) {


        return
    }
    console.log('this goal show', e.target.id);


    const modOpenDeleteS = document.querySelector(`.deleteS${e.target.id}`);
    modOpenDeleteS.classList.remove('hidden');
    modOpenDeleteS.classList.add('show');

});


document.addEventListener('click', function (e) {

    if (!e.target.classList.contains('btnDelCloseDown')) {


        return
    }
    console.log('this goal deleteCloseDown show', e.target.id);


    const modOpenDeleteS = document.querySelector(`.deleteS${e.target.id}`);

    modOpenDeleteS.classList.add('hidden');

});
document.addEventListener('click', function (e) {

    if (!e.target.classList.contains('btnDelCloseUp')) {


        return
    }
    console.log('this goal deleteCloseUp show', e.target.id);


    const modOpenDeleteS = document.querySelector(`.deleteS${e.target.id}`);

    modOpenDeleteS.classList.add('hidden');

});
document.addEventListener('click', function (e) {

    if (!e.target.classList.contains('btnDeleteClose')) {


        return
    }

    console.log('this goal deleteCloseUp show', e.target.id);


    const modOpenDeleteS = document.querySelector(`.deleteS${e.target.id}`);

    modOpenDeleteS.classList.add('hidden');

});
document.addEventListener('click', function (e) {

    if (!e.target.classList.contains('btnDeleteClose2')) {


        return
    }

    console.log('this goal deleteCloseUp show', e.target.id);


    const modOpenDeleteS = document.querySelector(`.deleteS${e.target.id}`);

    modOpenDeleteS.classList.add('hidden');

});

///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////// for popup create get from server /////////////////////////////////////////

getAllusers3.addEventListener('click', (evt) => {
    evt.preventDefault();
    createMethod();
})

function createMethod() {
    fetch('http://localhost:8080/api/createMod', {
        method: 'get',
        headers: {"Content-Type": "Content-Type: text/html"}
    })
        .then(function (response) {
            return response.text()
        })
        .then(function (data) {


            //  console.log('data', data);
            return logCreate(data.toString())

        })
}


function logCreate(data) {
    const logElemCreate = document.querySelector(".logCreate");


    logElemCreate.innerHTML = ` ${data} `;


    let quantityCreate = document.querySelectorAll(".createS").length;
    console.log('quantityCreate', quantityCreate);

    document.addEventListener('click', function (ev) {
            if (!ev.target.classList.contains('btnExpCreate')) {
                return
            }
            const createUser = document.querySelector(`.formCreate${ev.target.id}`);
            console.log("ccccccccccccc", createUser);


            createUser.addEventListener('submit', function (e) {
                e.preventDefault();
                console.log('this is delete button ', e.target.id);
                const formDataCry = new FormData(e.target);
                console.log(e.target);
                fetch(
                    `http://localhost:8080/api/users`, {
                        method: 'PUT',
                        body: formDataCry
                    },
                )

                    .then((response) => {
                        if (response.ok) {


                            callFetch();


                            getPass();


                        } else {


                            getWarning();
                        }
                    })
                    .catch(() => {
                        getWarning();

                    });


            });


        }
    )

}

///////////////////////////// eventListeners for create//////////////////////////////////////////

document.addEventListener('click', function (e) {

    if (!e.target.classList.contains('btnCreateCloseUp')) {


        return
    }
    console.log('this goal createCloseUp show', e.target.id);


    const modOpenCreateS = document.querySelector(`.createS${e.target.id}`);

    modOpenCreateS.classList.add('hidden');

});

document.addEventListener('click', function (e) {

    if (!e.target.classList.contains('btnCreateCloseDown')) {


        return
    }
    console.log('this goal createCloseDown show', e.target.id);


    const modOpenCreateS = document.querySelector(`.createS${e.target.id}`);

    modOpenCreateS.classList.add('hidden');

});


document.addEventListener('click', function (e) {

    if (!e.target.classList.contains('btnExpCreate')) {


        return
    }
    console.log('this goal create show', e.target.id);


    const modOpenCreateS = document.querySelector(`.createS${e.target.id}`);
    modOpenCreateS.classList.remove('hidden');

    modOpenCreateS.classList.add('show');

});
document.addEventListener('click', function (e) {

    if (!e.target.classList.contains('btnActiveCreateUp')) {


        return
    }
    console.log('this goal create show', e.target.id);


    const modOpenCreateS = document.querySelector(`.createS${e.target.id}`);
    modOpenCreateS.classList.add('hidden');


});
document.addEventListener('click', function (e) {

    if (!e.target.classList.contains('btnActiveCreateDown')) {


        return
    }
    console.log('this goal create show', e.target.id);


    const modOpenCreateS = document.querySelector(`.createS${e.target.id}`);
    modOpenCreateS.classList.add('hidden');


});
document.addEventListener('click', function (e) {

    if (!e.target.classList.contains('btnActiveCreateThis')) {


        return
    }
    console.log('this goal create show', e.target.id);


    const modOpenCreateS = document.querySelector(`.createS${e.target.id}`);
    modOpenCreateS.classList.remove('hidden');
    modOpenCreateS.classList.add('show');


});
/////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////// Active form delete this///////////////////////////////////////
const deleteUserThis = document.querySelector(".smallForm");


function callFetch() {

    fetch('http://localhost:8080/api/example', {
        method: 'get',
        headers: {"Content-Type": "Content-Type: text/html"}
    })
        .then(function (response) {
            return response.text()
        })
        .then(function (data) {


            //  console.log('data', data);
            return log(data.toString())

        })
}


deleteUserThis.addEventListener('submit', function (e) {
    e.preventDefault();
    console.log('this is delete button ', e.target.id);
    const formDataDelThis = new FormData(e.target);
    console.log(e.target);
    fetch(
        `http://localhost:8080/api/users`, {
            method: 'DELETE',
            body: formDataDelThis
        },
    )

        .then((response) => {
            if (response.ok) {

                callFetch();
                getPass();


            } else {


                getWarning();
            }
        })
        .catch(() => {
            getWarning();

        });
})

document.addEventListener('click', function (e) {

    if (!e.target.classList.contains('btnDeleteActive')) {


        return
    }
    console.log('this goal show', e.target.id);
    const modOpenDeleteS = document.querySelector(`.deleteS${e.target.id}`);
    modOpenDeleteS.classList.remove('show');
    modOpenDeleteS.classList.add('hidden');


});
//////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////active form edit//////////////////////////////////////////////////
const createUserThis = document.querySelector(".editForm");


createUserThis.addEventListener('submit', function (e) {
    e.preventDefault();
    console.log('this is create button ', e.target.id);
    const formDataCreateThis = new FormData(e.target);
    console.log(e.target);
    fetch(
        `http://localhost:8080/api/users`, {
            method: 'PUT',
            body: formDataCreateThis
        },
    )

        .then((response) => {
            if (response.ok) {

                callFetch();
                getPass();


            } else {


                getWarning();
            }
        })
        .catch(() => {
            getWarning();

        });
})

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//////////////////////////////////////// reload Page//////////////////////////////////////////////////////////////////////
document.addEventListener('click', function (e) {

    if (!e.target.classList.contains('user')) {


        return
    }
    console.log('this goal user');

    userMethod();


});

function userMethod() {
    fetch('http://localhost:8080/api/adminUserPage', {
        method: 'get',
        headers: {"Content-Type": "Content-Type: text/html"}
    })
        .then(function (response) {
            return response.text()
        })
        .then(function (data) {


            //  console.log('data', data);
            return logUser(data.toString())

        })
}

function logUser(data) {
    const logElemBody = document.querySelector(".body");


    logElemBody.innerHTML = ` ${data} `;


}

////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////reload adminUserPage////////////////////////////////////////////////

document.addEventListener('click', function (e) {

    if (!e.target.classList.contains('adminPageRef')) {


        return
    }
    console.log('this goal admin');
    adminMethod();
    deleteMethodGet();
    createMethod();
    adminUserMethod();
    getUsers();




});

function adminMethod() {
    fetch('http://localhost:8080/api/adminPage', {
        method: 'get',
        headers: {"Content-Type": "Content-Type: text/html"}
    })
        .then(function (response) {
            return response.text()
        })
        .then(function (data) {


            //  console.log('data', data);
            return logAdmin(data.toString())

        })
}

function logAdmin(data) {
    const logElemBody = document.querySelector(".body");


    logElemBody.innerHTML = ` ${data} `;

    console.log("fgd",data)




}
function adminUserMethod() {
    fetch('http://localhost:8080/api/adminPageMod', {
        method: 'get',
        headers: {"Content-Type": "Content-Type: text/html"}
    })
        .then(function (response) {
            return response.text()
        })
        .then(function (data) {


            //  console.log('data', data);
            return logAdminMod(data.toString())

        })
}

function logAdminMod(data) {
    const logElemBody = document.querySelector(".logLastProblem");


    logElemBody.innerHTML = ` ${data} `;
    const fff =document.querySelector(".fff");
    const ddd =document.querySelector(".ddd");




    document.addEventListener('click', function (e) {

        if (!e.target.classList.contains('popupClfff')) {

            return
        }
        fff.classList.add('show');
        fff.classList.remove('hidden');
        ddd.classList.add('hidden');
        ddd.classList.remove('show');

        console.log('this goal NEWUSER');

    });
    document.addEventListener('click', function (e) {

        if (!e.target.classList.contains('popupOpfff')) {

            return
        }
        fff.classList.add('hidden');
        fff.classList.remove('show');
        ddd.classList.add('show');
        ddd.classList.remove('hidden');

        console.log('this goal NEWUSER');


    });
    const formF =document.querySelector(".formF");

    formF.addEventListener('submit', (evt) => {
        evt.preventDefault();
        const formDataP = new FormData(evt.target);
        fetch(
            'http://localhost:8080/api/users', {
                method: 'POST',
                body: formDataP
            },
        )

            .then((response) => {
                if (response.ok) {


                    formF.reset();
                    getPass();


                } else {

                    formF.reset();
                    getWarning();
                }
            })
            .catch(() => {
                getWarning();

            });
    });

    const getAllusers4 = document.querySelector('.getAllUsersJSVr');

    getAllusers4.addEventListener('click', (evt) => {
        evt.preventDefault();
        getUsers2();
    })

    function getUsers2() {
        fetch('http://localhost:8080/api/example', {
            method: 'get',
            headers: {"Content-Type": "Content-Type: text/html"}
        })
            .then(function (response) {
                return response.text()
            })
            .then(function (data) {



                return log(data.toString())

            })
    }






}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



//
//     popupCloseAdminPanel.classList.add('show');
//     popupCloseAdminPanel.classList.remove('hidden');
//     popupCloseNewUser.classList.add('hidden');
//     popupCloseNewUser.classList.remove('show');
//
//
// });
