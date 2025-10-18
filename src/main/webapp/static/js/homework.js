function deleteHomework(id) {
    fetch("/homework?id=" + id, {method: "DELETE"})
        .then(response => {
            if (!response.ok) {
                throw new Error("Delete homework error: " + response.statusText);
            }
            return response;
        }).then(_ => location.reload());
}
