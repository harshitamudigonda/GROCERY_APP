import React, { useEffect, useState } from "react";
import config from "./config";  // import config

export default function App() {
  const [groceries, setGroceries] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");
  const [form, setForm] = useState({ name: "", quantity: 1 });
  const [editingId, setEditingId] = useState(null);
  const [editingForm, setEditingForm] = useState({ name: "", quantity: 1 });

  // Load groceries from backend
  async function loadList(query = "") {
    setLoading(true);
    const url = query 
      ? `${config.url}/groceries?search=${query}` 
      : `${config.url}/groceries`;   // use config.url here
    const res = await fetch(url);
    const data = await res.json();
    setGroceries(data);
    setLoading(false);
  }

  useEffect(() => {
    loadList();
  }, []);

  const handleSearch = (e) => {
    const value = e.target.value;
    setSearch(value);
    loadList(value);
  };

  async function handleAdd(e) {
    e.preventDefault();
    if (!form.name.trim()) return;
    const res = await fetch(`${config.url}/groceries`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(form),
    });
    const newItem = await res.json();
    setGroceries((prev) => [...prev, newItem]);
    setForm({ name: "", quantity: 1 });
  }

  async function handleDelete(id) {
    await fetch(`${config.url}/groceries/${id}`, { method: "DELETE" });
    setGroceries((prev) => prev.filter((item) => item.id !== id));
  }

  function startEdit(item) {
    setEditingId(item.id);
    setEditingForm({ name: item.name, quantity: item.quantity });
  }

  function cancelEdit() {
    setEditingId(null);
  }

  async function saveEdit(id) {
    const res = await fetch(`${config.url}/groceries/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(editingForm),
    });
    const updated = await res.json();
    setGroceries((prev) => prev.map((g) => (g.id === id ? updated : g)));
    setEditingId(null);
  }

  return (
    <div className="page">
      {/* ... rest of your JSX ... */}
    </div>
  );
}
