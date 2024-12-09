# Flight Booking System API Documentation

## Table of Contents
- [Authentication](#authentication)
- [Flights](#flights)
- [Planes](#planes)
- [Tickets](#tickets)
- [Orders](#orders)

## Authentication
Base path: `/api/auth`

### Sign In
```http
POST /signin
```
Authenticates a user and returns a JWT token in cookies.

**Request Body:**
```json
{
    "username": "string",
    "password": "string"
}
```

**Response:**
- `200 OK`: Successfully authenticated
- `404 Not Found`: Invalid credentials

### Sign Up
```http
POST /signup
```
Registers a new user.

**Request Body:**
```json
{
    "username": "string",
    "email": "string",
    "password": "string",
    "role": ["user", "admin", "seller"] // Optional
}
```

**Response:**
- `200 OK`: User registered successfully
- `400 Bad Request`: Username/email already exists

### Sign Out
```http
POST /signout
```
Signs out the current user by clearing the JWT cookie.

### Other Auth Endpoints
- `GET /username`: Get current user's username
- `GET /user`: Get current user's details
- `GET /health`: Health check endpoint for AWS

## Flights
Base path: `/api`

### Public Endpoints

#### Get All Flights
```http
GET /public/flights/all
```
Retrieves all flights with pagination.

**Query Parameters:**
- `pageNumber` (optional, default=0)
- `pageSize` (optional, default=10)
- `sortBy` (optional)
- `sortOrder` (optional, default="asc")

#### Search Flights
```http
GET /public/flights/search
```
Search flights by criteria.

**Query Parameters:**
- `departureCity` (required)
- `arrivalCity` (required)
- `date` (required, ISO format)
- `pageNumber` (optional)
- `pageSize` (optional)
- `sortBy` (optional)
- `sortOrder` (optional)

#### Get Flight by Number
```http
GET /public/flights/{flightNumber}
```

### Admin Endpoints
All admin endpoints require authentication with admin role.

#### Create Flight
```http
POST /admin/flights/create
```

#### Update Flight
```http
PUT /admin/flights/update/{flightId}
```

#### Update Flight Components
- `PUT /admin/flights/updatePlane/{flightId}`: Update flight's plane
- `PUT /admin/flights/changeFlightNumber/{flightId}`: Update flight number
- `PUT /admin/flights/changeFlightTime/{flightId}`: Update flight time
- `PUT /admin/flights/changeFlightLocation/{flightId}`: Update flight location
- `PUT /admin/flights/changeFlightPrice/{flightId}`: Update flight prices

#### Delete Flight
```http
DELETE /admin/deleteFlight/{flightId}
```

## Planes
Base path: `/api`

### Get All Planes
```http
GET /public/planes/all
```

### Admin Endpoints

#### Create Plane
```http
POST /admin/planes/create
```

#### Delete Plane
```http
DELETE /admin/planes/delete/{planeId}
```

## Tickets
Base path: `/api/tickets`

### Get Available Tickets
```http
GET /flight/{flightId}/available
```
Gets available tickets for a specific flight.

**Query Parameters:**
- `pageNumber` (optional)
- `pageSize` (optional)
- `sortBy` (optional)
- `sortOrder` (optional)

### Protected Endpoints
Requires AGENT or ADMIN role.

#### Get All Tickets for Flight
```http
GET /flight/{flightId}
```

#### Get Ticket by ID
```http
GET /{ticketId}
```

#### Update Ticket Status
```http
PUT /{ticketId}/setTicket/status
```

#### Update Ticket Image
```http
PUT /{ticketId}/setTicket/image
```
**Form Data:**
- `image`: MultipartFile

## Orders
Base path: `/api/order`

### Place Order
```http
POST /users/payments/{paymentMethod}
```
Places a new order for tickets.

**Path Parameters:**
- `paymentMethod`: Payment method to use

**Request Body:**
- Order details including ticket information

### Protected Endpoints
Requires authentication.

#### Get Current User
```http
GET /user
```
Requires AGENT role.

### Test Endpoints
- `GET /test/auth`: Test authentication
- `GET /order/test-roles`: Test user roles

## Common Response Codes
- `200 OK`: Successful operation
- `201 Created`: Resource successfully created
- `400 Bad Request`: Invalid request parameters
- `401 Unauthorized`: Authentication required
- `403 Forbidden`: Insufficient permissions
- `404 Not Found`: Resource not found
- `500 Internal Server Error`: Server error

## Notes
- All responses are in JSON format
- Protected endpoints require valid JWT token in cookies
- Pagination is available for list endpoints
- Date format follows ISO 8601 standard
