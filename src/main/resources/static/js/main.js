// Global variables
let isAdmin = false;
let token = null;

// DOM Elements
const loginBtn = document.getElementById('loginBtn');
const logoutBtn = document.getElementById('logoutBtn');
const usernameSpan = document.getElementById('username');
const navLinks = document.querySelectorAll('.nav-link');
const pages = document.querySelectorAll('.page');
const loginModal = document.getElementById('loginModal');
const loginForm = document.getElementById('loginForm');
const modal = document.getElementById('modal');
const modalContent = document.getElementById('modalContent');
const closeButtons = document.querySelectorAll('.close');

// API base URL
const API_URL = '/api';

// Event Listeners
document.addEventListener('DOMContentLoaded', init);
loginBtn.addEventListener('click', showLoginModal);
logoutBtn.addEventListener('click', logout);
loginForm.addEventListener('submit', login);

navLinks.forEach(link => {
    link.addEventListener('click', navigateTo);
});

closeButtons.forEach(button => {
    button.addEventListener('click', closeModal);
});

// Initialize the application
function init() {
    // Check if user is logged in
    const storedToken = localStorage.getItem('token');
    const storedUsername = localStorage.getItem('username');
    const storedRole = localStorage.getItem('role');
    
    if (storedToken && storedUsername) {
        token = storedToken;
        usernameSpan.textContent = `Welcome, ${storedUsername}`;
        loginBtn.style.display = 'none';
        logoutBtn.style.display = 'inline-block';
        
        if (storedRole === 'ADMIN') {
            isAdmin = true;
            document.querySelectorAll('.admin-only').forEach(el => {
                el.style.display = 'block';
            });
        }
        
        // Load dashboard data
        loadDashboardData();
    }
    
    // Add event listeners for action buttons
    document.getElementById('addShipBtn').addEventListener('click', () => showAddShipForm());
    document.getElementById('addCargoBtn').addEventListener('click', () => showAddCargoForm());
    document.getElementById('addPortBtn').addEventListener('click', () => showAddPortForm());
    document.getElementById('addUserBtn').addEventListener('click', () => showAddUserForm());
    
    // Load initial data
    loadShips();
    loadCargo();
    loadPorts();
    if (isAdmin) {
        loadUsers();
    }
}

// Navigation
function navigateTo(event) {
    event.preventDefault();
    const pageId = event.target.getAttribute('data-page');
    
    // Update active nav link
    navLinks.forEach(link => {
        link.classList.remove('active');
    });
    event.target.classList.add('active');
    
    // Show selected page
    pages.forEach(page => {
        page.classList.remove('active');
    });
    document.getElementById(pageId).classList.add('active');
    
    // Load data for the page if needed
    switch (pageId) {
        case 'ships':
            loadShips();
            break;
        case 'cargo':
            loadCargo();
            break;
        case 'ports':
            loadPorts();
            break;
        case 'users':
            if (isAdmin) {
                loadUsers();
            }
            break;
        case 'dashboard':
            loadDashboardData();
            break;
    }
}

// Authentication
function showLoginModal() {
    loginModal.style.display = 'block';
}

function closeModal() {
    loginModal.style.display = 'none';
    modal.style.display = 'none';
}

function login(event) {
    event.preventDefault();
    
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;
    
    // For demo purposes, we'll use a simple check
    // In a real application, this would be an API call
    if (username === 'admin' && password === 'admin') {
        token = 'demo-token';
        isAdmin = true;
        localStorage.setItem('token', token);
        localStorage.setItem('username', username);
        localStorage.setItem('role', 'ADMIN');
        
        usernameSpan.textContent = `Welcome, ${username}`;
        loginBtn.style.display = 'none';
        logoutBtn.style.display = 'inline-block';
        
        document.querySelectorAll('.admin-only').forEach(el => {
            el.style.display = 'block';
        });
        
        closeModal();
        loadDashboardData();
    } else if (username === 'user' && password === 'user') {
        token = 'demo-token';
        isAdmin = false;
        localStorage.setItem('token', token);
        localStorage.setItem('username', username);
        localStorage.setItem('role', 'USER');
        
        usernameSpan.textContent = `Welcome, ${username}`;
        loginBtn.style.display = 'none';
        logoutBtn.style.display = 'inline-block';
        
        closeModal();
        loadDashboardData();
    } else {
        alert('Invalid username or password');
    }
}

function logout() {
    token = null;
    isAdmin = false;
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('role');
    
    usernameSpan.textContent = 'Welcome, Guest';
    loginBtn.style.display = 'inline-block';
    logoutBtn.style.display = 'none';
    
    document.querySelectorAll('.admin-only').forEach(el => {
        el.style.display = 'none';
    });
    
    // Navigate to dashboard
    navLinks.forEach(link => {
        link.classList.remove('active');
        if (link.getAttribute('data-page') === 'dashboard') {
            link.classList.add('active');
        }
    });
    
    pages.forEach(page => {
        page.classList.remove('active');
        if (page.id === 'dashboard') {
            page.classList.add('active');
        }
    });
}

// API Calls
async function fetchData(endpoint) {
    try {
        const headers = {
            'Content-Type': 'application/json'
        };
        
        if (token) {
            headers['Authorization'] = `Bearer ${token}`;
        }
        
        const response = await fetch(`${API_URL}${endpoint}`, {
            method: 'GET',
            headers: headers
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error('Fetch error:', error);
        return null;
    }
}

async function postData(endpoint, data) {
    try {
        const headers = {
            'Content-Type': 'application/json'
        };
        
        if (token) {
            headers['Authorization'] = `Bearer ${token}`;
        }
        
        const response = await fetch(`${API_URL}${endpoint}`, {
            method: 'POST',
            headers: headers,
            body: JSON.stringify(data)
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error('Post error:', error);
        return null;
    }
}

// Data Loading Functions
async function loadDashboardData() {
    // For demo purposes, we'll use mock data
    // In a real application, this would be API calls
    
    // Update counts
    document.getElementById('shipCount').textContent = '5';
    document.getElementById('cargoCount').textContent = '12';
    document.getElementById('portCount').textContent = '8';
    
    // Populate recent ships table
    const recentShipsTable = document.getElementById('recentShipsTable').getElementsByTagName('tbody')[0];
    recentShipsTable.innerHTML = '';
    
    const mockShips = [
        { name: 'Oceanic Voyager', type: 'Cargo', status: 'Active', location: 'Port A' },
        { name: 'Northern Star', type: 'Passenger', status: 'Docked', location: 'Port B' },
        { name: 'Sea Explorer', type: 'Research', status: 'Maintenance', location: 'Port C' }
    ];
    
    mockShips.forEach(ship => {
        const row = recentShipsTable.insertRow();
        row.insertCell(0).textContent = ship.name;
        row.insertCell(1).textContent = ship.type;
        row.insertCell(2).textContent = ship.status;
        row.insertCell(3).textContent = ship.location;
    });
}

async function loadShips() {
    // For demo purposes, we'll use mock data
    // In a real application, this would be an API call: const ships = await fetchData('/ships');
    
    const shipsTable = document.getElementById('shipsTable').getElementsByTagName('tbody')[0];
    shipsTable.innerHTML = '';
    
    const mockShips = [
        { id: 1, name: 'Oceanic Voyager', type: 'Cargo', capacity: 5000, status: 'Active', location: 'Port A', destination: 'Port B' },
        { id: 2, name: 'Northern Star', type: 'Passenger', capacity: 2000, status: 'Docked', location: 'Port B', destination: 'Port C' },
        { id: 3, name: 'Sea Explorer', type: 'Research', capacity: 1000, status: 'Maintenance', location: 'Port C', destination: 'Port A' },
        { id: 4, name: 'Pacific Trader', type: 'Cargo', capacity: 4500, status: 'Active', location: 'Port D', destination: 'Port A' },
        { id: 5, name: 'Atlantic Cruiser', type: 'Passenger', capacity: 3000, status: 'Docked', location: 'Port A', destination: 'Port D' }
    ];
    
    mockShips.forEach(ship => {
        const row = shipsTable.insertRow();
        row.insertCell(0).textContent = ship.id;
        row.insertCell(1).textContent = ship.name;
        row.insertCell(2).textContent = ship.type;
        row.insertCell(3).textContent = ship.capacity;
        row.insertCell(4).textContent = ship.status;
        row.insertCell(5).textContent = ship.location;
        row.insertCell(6).textContent = ship.destination;
        
        const actionsCell = row.insertCell(7);
        actionsCell.innerHTML = `
            <button class="btn" onclick="editShip(${ship.id})">Edit</button>
            <button class="btn" style="background-color: var(--danger-color);" onclick="deleteShip(${ship.id})">Delete</button>
        `;
    });
}

async function loadCargo() {
    // For demo purposes, we'll use mock data
    const cargoTable = document.getElementById('cargoTable').getElementsByTagName('tbody')[0];
    cargoTable.innerHTML = '';
    
    const mockCargo = [
        { id: 1, type: 'Electronics', weight: 500, destination: 'Port B', status: 'Pending' },
        { id: 2, type: 'Food', weight: 1000, destination: 'Port C', status: 'In Transit' },
        { id: 3, type: 'Machinery', weight: 2000, destination: 'Port A', status: 'Delivered' },
        { id: 4, type: 'Textiles', weight: 800, destination: 'Port D', status: 'Pending' }
    ];
    
    mockCargo.forEach(cargo => {
        const row = cargoTable.insertRow();
        row.insertCell(0).textContent = cargo.id;
        row.insertCell(1).textContent = cargo.type;
        row.insertCell(2).textContent = cargo.weight;
        row.insertCell(3).textContent = cargo.destination;
        row.insertCell(4).textContent = cargo.status;
        
        const actionsCell = row.insertCell(5);
        actionsCell.innerHTML = `
            <button class="btn" onclick="editCargo(${cargo.id})">Edit</button>
            <button class="btn" style="background-color: var(--danger-color);" onclick="deleteCargo(${cargo.id})">Delete</button>
        `;
    });
}

async function loadPorts() {
    // For demo purposes, we'll use mock data
    const portsTable = document.getElementById('portsTable').getElementsByTagName('tbody')[0];
    portsTable.innerHTML = '';
    
    const mockPorts = [
        { id: 1, name: 'Port A', location: 'New York', capacity: 10000, status: 'Operational' },
        { id: 2, name: 'Port B', location: 'Los Angeles', capacity: 8000, status: 'Operational' },
        { id: 3, name: 'Port C', location: 'Miami', capacity: 6000, status: 'Maintenance' },
        { id: 4, name: 'Port D', location: 'Seattle', capacity: 7000, status: 'Operational' }
    ];
    
    mockPorts.forEach(port => {
        const row = portsTable.insertRow();
        row.insertCell(0).textContent = port.id;
        row.insertCell(1).textContent = port.name;
        row.insertCell(2).textContent = port.location;
        row.insertCell(3).textContent = port.capacity;
        row.insertCell(4).textContent = port.status;
        
        const actionsCell = row.insertCell(5);
        actionsCell.innerHTML = `
            <button class="btn" onclick="editPort(${port.id})">Edit</button>
            <button class="btn" style="background-color: var(--danger-color);" onclick="deletePort(${port.id})">Delete</button>
        `;
    });
}

async function loadUsers() {
    // For demo purposes, we'll use mock data
    const usersTable = document.getElementById('usersTable').getElementsByTagName('tbody')[0];
    usersTable.innerHTML = '';
    
    const mockUsers = [
        { id: 1, username: 'admin', email: 'admin@example.com', role: 'ADMIN' },
        { id: 2, username: 'user', email: 'user@example.com', role: 'USER' },
        { id: 3, username: 'john', email: 'john@example.com', role: 'USER' }
    ];
    
    mockUsers.forEach(user => {
        const row = usersTable.insertRow();
        row.insertCell(0).textContent = user.id;
        row.insertCell(1).textContent = user.username;
        row.insertCell(2).textContent = user.email;
        row.insertCell(3).textContent = user.role;
        
        const actionsCell = row.insertCell(4);
        actionsCell.innerHTML = `
            <button class="btn" onclick="editUser(${user.id})">Edit</button>
            <button class="btn" style="background-color: var(--danger-color);" onclick="deleteUser(${user.id})">Delete</button>
        `;
    });
}

// Form Functions
function showAddShipForm() {
    modalContent.innerHTML = `
        <h2>Add New Ship</h2>
        <form id="addShipForm">
            <div class="form-group">
                <label for="shipName">Name:</label>
                <input type="text" id="shipName" name="name" required>
            </div>
            <div class="form-group">
                <label for="shipType">Type:</label>
                <select id="shipType" name="type" required>
                    <option value="Cargo">Cargo</option>
                    <option value="Passenger">Passenger</option>
                    <option value="Research">Research</option>
                </select>
            </div>
            <div class="form-group">
                <label for="shipCapacity">Capacity:</label>
                <input type="number" id="shipCapacity" name="capacity" required>
            </div>
            <div class="form-group">
                <label for="shipStatus">Status:</label>
                <select id="shipStatus" name="status" required>
                    <option value="Active">Active</option>
                    <option value="Docked">Docked</option>
                    <option value="Maintenance">Maintenance</option>
                </select>
            </div>
            <div class="form-group">
                <label for="shipLocation">Current Location:</label>
                <input type="text" id="shipLocation" name="location" required>
            </div>
            <div class="form-group">
                <label for="shipDestination">Destination:</label>
                <input type="text" id="shipDestination" name="destination" required>
            </div>
            <button type="submit" class="btn">Add Ship</button>
        </form>
    `;
    
    modal.style.display = 'block';
    
    document.getElementById('addShipForm').addEventListener('submit', function(event) {
        event.preventDefault();
        // In a real application, this would call the API
        alert('Ship added successfully!');
        closeModal();
        loadShips();
    });
}

function showAddCargoForm() {
    modalContent.innerHTML = `
        <h2>Add New Cargo</h2>
        <form id="addCargoForm">
            <div class="form-group">
                <label for="cargoType">Type:</label>
                <input type="text" id="cargoType" name="type" required>
            </div>
            <div class="form-group">
                <label for="cargoWeight">Weight:</label>
                <input type="number" id="cargoWeight" name="weight" required>
            </div>
            <div class="form-group">
                <label for="cargoDestination">Destination:</label>
                <input type="text" id="cargoDestination" name="destination" required>
            </div>
            <div class="form-group">
                <label for="cargoStatus">Status:</label>
                <select id="cargoStatus" name="status" required>
                    <option value="Pending">Pending</option>
                    <option value="In Transit">In Transit</option>
                    <option value="Delivered">Delivered</option>
                </select>
            </div>
            <button type="submit" class="btn">Add Cargo</button>
        </form>
    `;
    
    modal.style.display = 'block';
    
    document.getElementById('addCargoForm').addEventListener('submit', function(event) {
        event.preventDefault();
        // In a real application, this would call the API
        alert('Cargo added successfully!');
        closeModal();
        loadCargo();
    });
}

function showAddPortForm() {
    modalContent.innerHTML = `
        <h2>Add New Port</h2>
        <form id="addPortForm">
            <div class="form-group">
                <label for="portName">Name:</label>
                <input type="text" id="portName" name="name" required>
            </div>
            <div class="form-group">
                <label for="portLocation">Location:</label>
                <input type="text" id="portLocation" name="location" required>
            </div>
            <div class="form-group">
                <label for="portCapacity">Capacity:</label>
                <input type="number" id="portCapacity" name="capacity" required>
            </div>
            <div class="form-group">
                <label for="portStatus">Status:</label>
                <select id="portStatus" name="status" required>
                    <option value="Operational">Operational</option>
                    <option value="Maintenance">Maintenance</option>
                </select>
            </div>
            <button type="submit" class="btn">Add Port</button>
        </form>
    `;
    
    modal.style.display = 'block';
    
    document.getElementById('addPortForm').addEventListener('submit', function(event) {
        event.preventDefault();
        // In a real application, this would call the API
        alert('Port added successfully!');
        closeModal();
        loadPorts();
    });
}

function showAddUserForm() {
    modalContent.innerHTML = `
        <h2>Add New User</h2>
        <form id="addUserForm">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="role">Role:</label>
                <select id="role" name="role" required>
                    <option value="USER">User</option>
                    <option value="ADMIN">Admin</option>
                </select>
            </div>
            <button type="submit" class="btn">Add User</button>
        </form>
    `;
    
    modal.style.display = 'block';
    
    document.getElementById('addUserForm').addEventListener('submit', function(event) {
        event.preventDefault();
        // In a real application, this would call the API
        alert('User added successfully!');
        closeModal();
        loadUsers();
    });
}

// Edit and Delete Functions
function editShip(id) {
    alert(`Edit ship with ID: ${id}`);
    // In a real application, this would open a form with the ship's data
}

function deleteShip(id) {
    if (confirm(`Are you sure you want to delete ship with ID: ${id}?`)) {
        // In a real application, this would call the API
        alert(`Ship with ID: ${id} deleted successfully!`);
        loadShips();
    }
}

function editCargo(id) {
    alert(`Edit cargo with ID: ${id}`);
    // In a real application, this would open a form with the cargo's data
}

function deleteCargo(id) {
    if (confirm(`Are you sure you want to delete cargo with ID: ${id}?`)) {
        // In a real application, this would call the API
        alert(`Cargo with ID: ${id} deleted successfully!`);
        loadCargo();
    }
}

function editPort(id) {
    alert(`Edit port with ID: ${id}`);
    // In a real application, this would open a form with the port's data
}

function deletePort(id) {
    if (confirm(`Are you sure you want to delete port with ID: ${id}?`)) {
        // In a real application, this would call the API
        alert(`Port with ID: ${id} deleted successfully!`);
        loadPorts();
    }
}

function editUser(id) {
    alert(`Edit user with ID: ${id}`);
    // In a real application, this would open a form with the user's data
}

function deleteUser(id) {
    if (confirm(`Are you sure you want to delete user with ID: ${id}?`)) {
        // In a real application, this would call the API
        alert(`User with ID: ${id} deleted successfully!`);
        loadUsers();
    }
}
